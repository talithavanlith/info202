/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author talithavanlith
 */
public class DAOException extends RuntimeException {

    /**
     * Creates a new instance of <code>DAOException</code> with the reason 
     * and cause.
     * @param reason the exception reason.
     * @param cause another exception.
     */
    public DAOException(String reason, Throwable cause) {
        super(reason, cause);
    }

    /**
     * Constructs an instance of <code>DAOException</code> with the specified
     * detail message.
     *
     * @param reason the exception reason.
     */
    public DAOException(String reason) {
        super(reason);
    }
}
