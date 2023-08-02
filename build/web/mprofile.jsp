<%-- 
    Document   : mprofile
    Created on : Jun 13, 2022, 2:41:44 PM
    Author     : TRAIN 89
--%>

<%@page import="com.lib.bean.Member"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Responsive Bootstrap4 Shop Template, Created by Imran Hossain from https://imransdesign.com/">

        <!-- title -->
        <title>Profile</title>

        <!-- favicon -->
        <link rel="shortcut icon" type="image/png" href="assets/img/favicon.png">
        <!-- google font -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,700&display=swap" rel="stylesheet">
        <!-- fontawesome -->
        <link rel="stylesheet" href="assets/css/all.min.css">
        <!-- bootstrap -->
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <!-- owl carousel -->
        <link rel="stylesheet" href="assets/css/owl.carousel.css">
        <!-- magnific popup -->
        <link rel="stylesheet" href="assets/css/magnific-popup.css">
        <!-- animate css -->
        <link rel="stylesheet" href="assets/css/animate.css">
        <!-- mean menu css -->
        <link rel="stylesheet" href="assets/css/meanmenu.min.css">
        <!-- main style -->
        <link rel="stylesheet" href="assets/css/main.css">
        <!-- responsive -->
        <link rel="stylesheet" href="assets/css/responsive.css">

    </head>
    <body>

        <% Member mem = (Member) session.getAttribute("mem");%>

        <!--PreLoader-->
        <div class="loader">
            <div class="loader-inner">
                <div class="circle"></div>
            </div>
        </div>
        <!--PreLoader Ends-->

        <!-- header -->
        <div class="top-header-area" id="sticker">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 col-sm-12 text-center">
                        <div class="main-menu-wrap">
                            <!-- logo -->
                            <div class="site-logo">
                                <a href="mhome.jsp">
                                    <img src="assets/img/logo.png" alt="">
                                </a>
                            </div>
                            <!-- logo -->

                            <!-- menu start -->
                            <nav class="main-menu">
                                <ul>
                                    <li><a href="mhome.jsp">Home</a></li>

                                    <li><a href="mloans.jsp">Loans</a></li>

                                    <li><a href="mreserv.jsp">Reservations</a></li>

                                    <li><a href="lib?pro=category&action=getall">Book Store</a></li>

                                    <li class="current-list-item"><a href="mprofile.jsp">Profile</a></li>

                                    <li>
                                        <div class="header-icons">
                                            <a class="shopping-cart" href="cart.jsp"><i class="fas fa-shopping-cart"></i></a>
                                            <a class="shopping-cart" href="lib?pro=member&action=logout"><i class="fas fa-power-off"></i></a>
                                        </div>
                                    </li>
                                </ul>
                            </nav>
                            <a class="mobile-show search-bar-icon" href="#"><i class="fas fa-search"></i></a>
                            <div class="mobile-menu"></div>
                            <!-- menu end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end header -->

        <!-- search area -->
        <div class="search-area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <span class="close-btn"><i class="fas fa-window-close"></i></span>
                        <div class="search-bar">
                            <div class="search-bar-tablecell">
                                <h3>Search For:</h3>
                                <input type="text" placeholder="Keywords">
                                <button type="submit">Search <i class="fas fa-search"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end search arewa -->

        <!-- breadcrumb-section -->
        <div class="breadcrumb-section breadcrumb-bg">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 offset-lg-2 text-center">
                        <div class="breadcrumb-text">
                            <h1>Profile</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end breadcrumb section -->

        <!-- contact form -->
        <div class="contact-from-section mt-150 mb-150">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 mb-5 mb-lg-0">
                        <div class="form-title">
                            <h2>Update Details</h2>
                            <% if (request.getAttribute("msg") != null) {%>
                            <h4><%=request.getAttribute("msg")%></h4>
                            <%}%>

                        </div>
                        <div id="form_status"></div>
                        <div class="contact-form">
                            <form type="POST" action="lib">

                                <input type="hidden" name="pro" value="member" required >
                                <input type="hidden" name="action" value="update" required >
                                <p>
                                    <input type="text" placeholder="Name" name="name" id="name" value="<%= mem.getName()%>">
                                    <input type="text" placeholder="surname" name="surname" id="surname" value="<%= mem.getSurname()%>">
                                </p>

                                <p>
                                    <input type="tel" placeholder="Work Number" name="workTel" id="workTel" value="<%= mem.getWorkTel()%>">
                                    <input type="tel" placeholder="Mobile Number" name="mobileTel" id="mobileTel" value="<%= mem.getMobileTel()%>">
                                </p>

                                <p>

                                    <input type="hidden" placeholder="Email" name="email" id="email" value="<%= mem.getEmail()%>">
                                    <input type="hidden" placeholder="password" name="password" id="password" value="<%= mem.getPassword()%>">

                                </p>
                                <p><textarea name="address" id="address" cols="30" rows="10" placeholder="Address"><%= mem.getAddress()%></textarea></p>
                                <p><input type="submit" value="Update" name="update"></p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end contact form -->

        <!-- jquery -->
        <script src="assets/js/jquery-1.11.3.min.js"></script>
        <!-- bootstrap -->
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <!-- count down -->
        <script src="assets/js/jquery.countdown.js"></script>
        <!-- isotope -->
        <script src="assets/js/jquery.isotope-3.0.6.min.js"></script>
        <!-- waypoints -->
        <script src="assets/js/waypoints.js"></script>
        <!-- owl carousel -->
        <script src="assets/js/owl.carousel.min.js"></script>
        <!-- magnific popup -->
        <script src="assets/js/jquery.magnific-popup.min.js"></script>
        <!-- mean menu -->
        <script src="assets/js/jquery.meanmenu.min.js"></script>
        <!-- sticker js -->
        <script src="assets/js/sticker.js"></script>
        <!-- form validation js -->
        <script src="assets/js/form-validate.js"></script>
        <!-- main js -->
        <script src="assets/js/main.js"></script>

    </body>
</html>