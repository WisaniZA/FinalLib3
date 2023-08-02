/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.service;


import com.lib.process.ProcessRequest;
import java.util.Date;


/**
 *
 * @author TRAIN 79
 */
public interface ReservationService extends ProcessRequest {
    
    boolean createReservation(int reserveId, int bookId, int memberId, Date reserveDate);
    boolean removeReservation(int reserveId);
}