package com.lib.dao;

import java.util.Date;

public interface ReservationDao {
    boolean createReservation(int reserveId, int bookId, int memberId, Date reserveDate);
    boolean removeReservation(int reserveId);
}
