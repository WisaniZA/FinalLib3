package com.lib.dao.impl;

import com.lib.bean.Author;
import com.lib.bean.Book;
import com.lib.dao.BookDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class BookDaoImpl implements BookDao {

    private BasicDataSource bds = null;
    private Connection con = null;
    private Connection con1 = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private ResultSet rs = null;
    boolean bool;

    public BookDaoImpl(BasicDataSource datasource) {
        bds = datasource;
    }

// ***************************************************************************************************************    
    @Override
    public boolean removeBookItem(int bookId) {
        return setBookItemStatus(bookId, true);
    }

// ***************************************************************************************************************  
    @Override
    public boolean setBookItemStatus(int bookId, boolean status) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE book_item SET deleted = ? WHERE book_id=?");
                ps.setBoolean(1, status);
                ps.setInt(2, bookId);
                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return bool;
    }
// ***************************************************************************************************************  

    @Override
    public boolean removeBook(String isbn) {
        return setBookStatus(isbn, true);
    }

    // *************************************************************************************************************** 
    @Override
    public boolean setBookStatus(String isbn, boolean status) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE book SET deleted = ? WHERE isbn=?");
                ps.setBoolean(1, status);
                ps.setString(2, isbn);
                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return bool;
    }

    // *************************************************************************************************************** 
    @Override
    public List<Book> getBook(String searchedWord) {
        List<Book> book = new ArrayList<>();
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT B.isbn, B.cat_id, title,pub_date, rack_num,B.deleted FROM book B, book_author BA, author A, category C\n"
                        + "WHERE B.isbn=BA.isbn AND A.auth_id = BA.auth_id\n"
                        + "AND B.cat_id = C.cat_id\n"
                        + "AND B.deleted = FALSE\n"
                        + "AND CONCAT(B.title,A.name,A.surname,C.genre,B.pub_date) LIKE ?");
                if (searchedWord == null) {
                    ps.setString(1, "%%");
                } else {
                    ps.setString(1, "%"+searchedWord.toLowerCase()+"%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    book.add(new Book(rs.getString("B.isbn"), rs.getInt("B.cat_id"), rs.getString("title"),rs.getString("pub_date"),rs.getString("rack_num"),rs.getBoolean("B.deleted")));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return book;
    }

    // *************************************************************************************************************** 
    @Override
    public boolean updateBook(Book book) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE book SET cat_id=?, title=?, pub_date=?,rack_num=? WHERE isbn=?;");
                ps.setInt(1, book.getCatId());
                ps.setString(2, book.getTitle());
                ps.setString(3, book.getPubDate());
                ps.setString(4, book.getRackNum());
                ps.setString(5, book.getIsbn());
                if (ps.executeUpdate() > 0) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return bool;
    }

    // *************************************************************************************************************** 
    public void closePsCon(PreparedStatement preStm, Connection con) {
        if (preStm != null) {
            try {
                preStm.close();
            } catch (SQLException ex) {
                System.out.println("Could not close: " + ex.getMessage());
            }
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }
    }

    // ***************************************************************************************************************
    @Override
    public boolean addBook(Book book) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO book VALUES(?,?,?,?,?,?)");
                ps.setString(1, book.getIsbn());
                ps.setInt(2, book.getCatId());
                ps.setString(3, book.getTitle());
                ps.setString(4, book.getPubDate());
                ps.setString(5, book.getRackNum());
                ps.setBoolean(6, book.isDeleted());
                if (!(ps.executeUpdate() > 0)) {
                    return bool;
                }
                con1 = bds.getConnection();
                con1.setAutoCommit(false);
                ps1 = con1.prepareStatement("INSERT INTO book_author VALUES(?,?)");
                for (Author author : book.getAuthors()) {
                    try {
                        ps1.setString(1, book.getIsbn());
                        ps1.setInt(2, author.getAuthId());
                        ps1.addBatch();
                    } catch (SQLException e) {
                        System.out.println("Error: Author with ID: " + author.getAuthId() + " does not exist");
                        con.rollback();
                        con1.rollback();
                        return bool;
                    }
                }
                int[] insertedNumber = ps1.executeBatch();
                // TODO: check that all inserted correctly, if not, rollback
                con.commit();
                con1.commit();
                bool = true;
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                }
                try {
                    con1.setAutoCommit(true);
                } catch (SQLException ex) {
                }
                closePsCon(ps, con);
                closePsCon(ps1, con1);
            }
        }
        return bool;
    }

    // *************************************************************************************************************** 
    @Override
    public boolean addBookItem(String isbn, int numberOfBooks) {
        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO book_item(book_id, isbn, on_loan, deleted) VALUES(null,?,false,false)");
                while (numberOfBooks > 0) {
                    ps.setString(1, isbn);
                    ps.addBatch();
                    ps.addBatch();
                    numberOfBooks -= 1;
                }
                int[] insertedNumber = ps1.executeBatch();
                // check that all inserted correctly, if not, rollback
                con.commit();
                bool = true;
            } catch (SQLException e) {
                System.out.println("Error: Adding Book Items failed: " + e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error occurred so rolledback: " + ex.getMessage());
                }
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                }
                closePsCon(ps, con);
            }
        }
        return bool;
    }
    // *************************************************************************************************************** 

    @Override
    public Book getActualBook(String isbn) {
        Book book = null;
        List<Author> authors = new ArrayList<Author>();
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT a.auth_id, a.name, a.surname, a.deleted "
                        + "FROM author AS a, book_author as ba "
                        + "WHERE ba.isbn = 123 AND ba.auth_id=a.auth_id");
                ps.setString(1, isbn);
                rs = ps.executeQuery();
                while (rs.next()) {
                    authors.add(new Author(
                            rs.getInt("auth_id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBoolean("deleted")
                    ));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT isbn, cat_id, title, pub_date, rack_num, deleted FROM book WHERE isbn = ?");
                ps.setString(1, isbn);
                rs = ps.executeQuery();
                if (rs.next()) {
                    book = new Book(
                            rs.getString("isbn"),
                            rs.getInt("cat_id"),
                            rs.getString("title"),
                            rs.getString("pub_date"),
                            rs.getString("rack_num"),
                            authors
                            
                    );
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return book;
    }

     @Override
    public List<Book> getBook() {
        List<Book> book = new ArrayList<>();
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT B.isbn, B.cat_id, title,pub_date, rack_num,B.deleted FROM book B, book_author BA, author A, category C\n"
                        + "WHERE B.isbn=BA.isbn AND A.auth_id = BA.auth_id\n"
                        + "AND B.cat_id = C.cat_id\n"
                        + "AND B.deleted = FALSE\n");
               
                rs = ps.executeQuery();
                while (rs.next()) {
                    book.add(new Book(rs.getString("B.isbn"), rs.getInt("B.cat_id"), rs.getString("title"), rs.getString("pub_date"), rs.getString("rack_num"), rs.getBoolean("B.deleted")));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
                con = null;
            }
        }
        return book;
    }
}
