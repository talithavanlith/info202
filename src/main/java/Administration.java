
import dao.ProductDatabase;
import gui.MainMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vanta342
 */
public class Administration {
    
    public static void main(String[] args) {
        ProductDatabase dao = new ProductDatabase();
        
        // create the frame instance
        MainMenu frame = new MainMenu(dao);

        // centre the frame on the screen
        frame.setLocationRelativeTo(null);

        // show the frame
        frame.setVisible(true);
        
        
    }
}
