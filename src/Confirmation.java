import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aris Prabowo
 */
public class Confirmation {
    FormUtama FormUtama = new FormUtama();
    
    public void Cancel(JPanel Destination){
        int CancelDialog = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin untuk membatalkan?", "Warning", JOptionPane.YES_NO_OPTION);
        if(CancelDialog == JOptionPane.YES_OPTION)
            FormUtama.NavigateTo(Destination);
    }
    public void LogOut(JPanel Destination){
        int CancelDialog = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin untuk keluar?", "Warning", JOptionPane.YES_NO_OPTION);
        if(CancelDialog == JOptionPane.YES_OPTION)
            FormUtama.NavigateTo(Destination);
    }
}
