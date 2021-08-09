import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JPanel;
import java.util.LinkedList;
import java.util.AbstractMap;
import javax.swing.JOptionPane;
import java.util.LinkedHashMap;
import java.awt.event.ItemEvent;
import edu.kemahasiswaan.table.*;
import edu.kemahasiswaan.handler.*;
import edu.kemahasiswaan.controller.*;
import edu.kemahasiswaan.validation.*;
import edu.kemahasiswaan.state.FormState;
import edu.kemahasiswaan.response.Response;
import edu.kemahasiswaan.helper.DateHelper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aris Prabowo
 * @author T Doni
 */
public class FormUtama extends javax.swing.JFrame 
{
    private FormState _mahasiswaFormState;
    private FormState _transaksiFormState;
    private FormState _mataKuliahFormState;
    private FormState _nilaiMahasiswaFormState;
    private FormState _simulasiNilaiFormState;
    
    private MahasiswaValidation _mahasiswaValidation;
    private TransaksiValidation _transaksiValidation;
    private MataKuliahValidation _mataKuliahValidation;
    private SimulasiNilaiValidation _simulasiNilaiValidation;
    private NilaiMahasiswaValidation _nilaiMahasiswaValidation;
    
    private MahasiswaController _mahasiswaController;
    private TransaksiController _transaksiController;
    private MataKuliahController _mataKuliahController;
    private SimulasiNilaiController _simulasiNilaiController;
    private NilaiMahasiswaController _nilaiMahasiswaController;
    
    private JTableHandler<Mahasiswa> _mahasiswaTableHandler;
    private JTableHandler<Transaksi> _transaksiTableHandler;
    private JTableHandler<MataKuliah> _mataKuliahTableHandler;
    private JTableHandler<SimulasiNilai> _simulasiNilaiTableHandler;
    private JTableHandler<NilaiMahasiswa> _nilaiMahasiswaTableHandler;
    
    private JTextFieldHandler<Mahasiswa> _mahasiswaTextFieldHandler;
    private JTextFieldHandler<Transaksi> _transaksiTextFieldHandler;
    private JTextFieldHandler<MataKuliah> _mataKuliahTextFieldHandler;
    private JTextFieldHandler<SimulasiNilai> _simulasiNilaiTextFieldHandler;
    private JTextFieldHandler<NilaiMahasiswa> _nilaiMahasiswaTextFieldHandler;
    
    private JComboBoxHandler<Transaksi> _transaksiComboBoxHandler;
    private JComboBoxHandler<SimulasiNilai> _simulasiNilaiComboBoxHandler;
    private JComboBoxHandler<NilaiMahasiswa> _nilaiMahasiswaComboBoxHandler;
    
    private final HashMap<String, LinkedList<Integer>> _operatorNilaiData;
    
    
    public FormUtama() 
    {
        initComponents();
        InitializeHandler();
        InitializeFormValidation();
        InitializeController();
        LoadTable();
        
        _operatorNilaiData = (HashMap<String, LinkedList<Integer>>) _transaksiController.SelectAllOperatorNominal().GetResult().getFirst().get(Transaksi.Operator);
        LoadComboBox();
        LoadComboBoxEvent();
    }
    
    // <editor-fold desc="Initialize">
    private void InitializeHandler()
    {
        InitializeTableHandler();
        InitializeTextFieldHandler();
        InitializeComboBoxHandler();
    }

    private void InitializeComboBoxHandler()
    {
        _nilaiMahasiswaComboBoxHandler = new JComboBoxHandler(new LinkedHashMap<>()
        {{
            put(NilaiMahasiswa.NamaMhs, FormNilaiNama);
            put(NilaiMahasiswa.NamaMk, FormNilaiNamaMataKuliah);
        }});
        
        _simulasiNilaiComboBoxHandler = new JComboBoxHandler(new LinkedHashMap<>() {{
            put(SimulasiNilai.NamaMk, FormSimulasiAkhirNamaMataKuliah);
        }});
        
        _transaksiComboBoxHandler = new JComboBoxHandler(new LinkedHashMap<>() {{
            put(Transaksi.Operator, FormSimulasiKasusOperator);
            put(Transaksi.Nominal, FormSimulasiKasusNominal);
            put(Transaksi.MetodePembayaran, FormSimulasiKasusMetodePembayaran);
            put(Transaksi.Status, FormSimulasiKasusStatus);
        }});
    }
    
    private void InitializeTableHandler()
    {
        _mataKuliahTableHandler = new JTableHandler<>(TableMatkul, MataKuliah.No);
        _mahasiswaTableHandler = new JTableHandler<>(TableMahasiswa, Mahasiswa.Nim);
        
        _nilaiMahasiswaTableHandler = new JTableHandler<>(TableNilai, NilaiMahasiswa.No);
        TableNilai.removeColumn(TableNilai.getColumnModel().getColumn(0));
        
        _simulasiNilaiTableHandler = new JTableHandler<>(TableSimulasiAkhir, SimulasiNilai.No);
        TableSimulasiAkhir.removeColumn(TableSimulasiAkhir.getColumnModel().getColumn(0));
        
        _transaksiTableHandler = new JTableHandler<>(TableSimulasiKasus, Transaksi.id);
    }

    private void InitializeTextFieldHandler()
    {
        _mahasiswaTextFieldHandler = new JTextFieldHandler(new LinkedHashMap<>()
        {{
            put(Mahasiswa.Nim, FromMahasiswaNIM);
            put(Mahasiswa.Nama, FromMahasiswaNama);
            put(Mahasiswa.Alamat, FromMahasiswaAlamat);
            put(Mahasiswa.TempatLahir, FromMahasiswaTempatLahir);
            put(Mahasiswa.TanggalLahir, FromMahasiswaTanggalLahir);
        }});

        _mataKuliahTextFieldHandler = new JTextFieldHandler(new LinkedHashMap<>()
        {{
            put(MataKuliah.No, FromMataKuliahNomor);
            put(MataKuliah.Nama, FromMataKuliahNama);
        }});
        
        _nilaiMahasiswaTextFieldHandler = new JTextFieldHandler(new LinkedHashMap<>()
        {{
            put(NilaiMahasiswa.Nim, FormNilaiNim);
            put(NilaiMahasiswa.NoMk, FormNilaiKodeMataKuliah);
            put(NilaiMahasiswa.Kehadiran, FormNilaiKehadiran);
            put(NilaiMahasiswa.TugasPertama, FormNilaiTugas1);
            put(NilaiMahasiswa.TugasKedua, FormNilaiTugas2);
            put(NilaiMahasiswa.TugasKetiga, FormNilaiTugas3);
            put(NilaiMahasiswa.Uts, FormNilaiUTS);
            put(NilaiMahasiswa.Uas, FormNilaiUAS);
            put(NilaiMahasiswa.Angkatan, FormNilaiAngkatan);
        }});
        
        _simulasiNilaiTextFieldHandler = new JTextFieldHandler(new LinkedHashMap<>(){{
            put(SimulasiNilai.NoMk, FormSimulasiAkhirKodeMataKuliah);
            put(SimulasiNilai.PresentaseKehadiran, FormSimulasiAkhirPresentaseAbsen);
            put(SimulasiNilai.PresentaseTugas, FormSimulasiAkhirPresentaseTugas);
            put(SimulasiNilai.PresentaseUts, FormSimulasiAkhirPresentaseUTS);
            put(SimulasiNilai.PresentaseUas, FormSimulasiAkhirPresentaseUAS);
            put(SimulasiNilai.Kehadiran, FormSimulasiAkhirKehadiran);
            put(SimulasiNilai.TugasPertama, FormSimulasiAkhirTugas1);
            put(SimulasiNilai.TugasKedua, FormSimulasiAkhirTugas2);
            put(SimulasiNilai.TugasKetiga, FormSimulasiAkhirTugas3);
            put(SimulasiNilai.Uts, FormSimulasiAkhirUTS);
            put(SimulasiNilai.Uas, FormSimulasiAkhirUAS);
        }});
        
        _transaksiTextFieldHandler = new JTextFieldHandler<>(new LinkedHashMap<>()
        {{
            put(Transaksi.NomorTelp, FormSimulasiKasusNoTelp);
        }});
    }

    private void InitializeFormValidation()
    {
        _mahasiswaValidation = new MahasiswaValidation(_mahasiswaTableHandler, _mahasiswaTextFieldHandler);
        _mataKuliahValidation = new MataKuliahValidation(_mataKuliahTableHandler, _mataKuliahTextFieldHandler);
        _transaksiValidation = new TransaksiValidation(_transaksiTableHandler, _transaksiTextFieldHandler, _transaksiComboBoxHandler);
        _simulasiNilaiValidation = new SimulasiNilaiValidation(_simulasiNilaiTableHandler, _simulasiNilaiTextFieldHandler, _simulasiNilaiComboBoxHandler);
        _nilaiMahasiswaValidation = new NilaiMahasiswaValidation(_nilaiMahasiswaTableHandler, _nilaiMahasiswaTextFieldHandler, _nilaiMahasiswaComboBoxHandler);
    }

    private void InitializeController()
    {
        _mahasiswaController = new MahasiswaController(_mahasiswaValidation);
        _transaksiController = new TransaksiController(_transaksiValidation);
        _mataKuliahController = new MataKuliahController(_mataKuliahValidation);
        _simulasiNilaiController = new SimulasiNilaiController(_simulasiNilaiValidation);
        _nilaiMahasiswaController = new NilaiMahasiswaController(_nilaiMahasiswaValidation);
    }
    // </editor-fold>
    
    // <editor-fold desc="Button Callback">
    private void CreateFormButtonCallback(Response response, JTableHandler tableHandler, JTextFieldHandler textFieldHandler, JPanel panel)
    {
        if(response == null) return;
        tableHandler.Add((Map) response.GetResult().getFirst());
        textFieldHandler.ResetAllText();
        NavigateTo(panel);
    }
    
    private void UpdateFormButtonCallback(Response response, JTableHandler tableHandler, JTextFieldHandler textFieldHandler, JPanel panel)
    {
        if(response == null) return;
        tableHandler.Update((Map) response.GetResult().getFirst());
        textFieldHandler.ResetAllText();
        NavigateTo(panel);
    }
    
    private void UpdateButtonCallback(JTableHandler tableHandler, JTextFieldHandler textFieldHandler, JPanel panel, String namaTabel)
    {
        int selectedRow = tableHandler.GetSelectedRowIndex();
        if(selectedRow == -1)
        {
            JOptionPane.showMessageDialog(null, 
                "Maaf, silahkan pilih baris pada tabel "+namaTabel+" terlebih dahulu", "Error", 
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        textFieldHandler.SetAllText(tableHandler.GetRowValue(selectedRow));
        NavigateTo(panel);
    }
    
    private void DeleteButtonCallback(Response response, JTableHandler tableHandler)
    {
        if(response == null) return;
        tableHandler.Delete((Map)response.GetResult().getFirst());
    }
    // </editor-fold>
    
    private void LoadTable()
    {
        LinkedList<Map<Mahasiswa, Object>> tableMahasiswaData = _mahasiswaController.SelectAll().GetResult();
        _mahasiswaTableHandler.Load(tableMahasiswaData);
        
        LinkedList<Map<MataKuliah, Object>> tableMataKuliahData = _mataKuliahController.SelectAll().GetResult();
        _mataKuliahTableHandler.Load(tableMataKuliahData);
        
        LinkedList<Map<NilaiMahasiswa, Object>> tableNilaiMahasiswa = _nilaiMahasiswaController.SelectAll().GetResult();
        _nilaiMahasiswaTableHandler.Load(tableNilaiMahasiswa);
         
        LinkedList<Map<SimulasiNilai, Object>> tableSimulasiNilai = _simulasiNilaiController.SelectAll().GetResult();
        _simulasiNilaiTableHandler.Load(tableSimulasiNilai);
        
        LinkedList<Map<Transaksi, Object>> tableTransaksi = _transaksiController.SelectAll().GetResult();
        _transaksiTableHandler.Load(tableTransaksi);
    }
    
    private void LoadComboBox()
    {
        LoadComboBoxNilaiMahasiswa();
        LoadComboBoxSimulasiNilai();
        LoadComboBoxTransaksi();
        LoadNominalComboBox();
    }
    
    private void LoadComboBoxNilaiMahasiswa()
    {
        LinkedList<Object> dataMahasiswa = _mahasiswaTableHandler.GetColumnValue(Mahasiswa.Nama);
        LinkedList<Object> dataMataKuliah = _mataKuliahTableHandler.GetColumnValue(MataKuliah.Nama);
        
        _nilaiMahasiswaComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(NilaiMahasiswa.NamaMhs, dataMahasiswa));
        _nilaiMahasiswaComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(NilaiMahasiswa.NamaMk, dataMataKuliah));
    }
    
    private void LoadComboBoxSimulasiNilai()
    {
        LinkedList<Object> dataMataKuliah = _mataKuliahTableHandler.GetColumnValue(MataKuliah.Nama);
        _simulasiNilaiComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(SimulasiNilai.NamaMk, dataMataKuliah));
    }
    
    private void LoadComboBoxTransaksi(){
        _transaksiComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(Transaksi.Operator, new LinkedList<>(_operatorNilaiData.keySet())));
        
        LinkedList<Object> result = (LinkedList<Object>)_transaksiController.SelectAllMetodePembayaran().GetResult().getFirst().get(Transaksi.MetodePembayaran);
        _transaksiComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(Transaksi.MetodePembayaran,  result));
        
        _transaksiComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(Transaksi.Status,  new LinkedList<Object>() {{add("Lunas"); add("Belum Lunas");}}));
    }
    
    private void LoadNominalComboBox()
    {
        _transaksiComboBoxHandler.Load(new AbstractMap.SimpleEntry<>(Transaksi.Nominal, new LinkedList<>(_operatorNilaiData.get(_transaksiComboBoxHandler.GetSelectedItem(Transaksi.Operator)))));
    }
    
    public void NavigateTo(JPanel Destination)
    {
        PanelContent.removeAll();
        PanelContent.repaint();
        PanelContent.revalidate();
        PanelContent.add(Destination);
        PanelContent.repaint();
        PanelContent.revalidate();
    }
    
    private void CancelConfirmation(JTextFieldHandler textFieldHandler, JPanel Destination)
    {
        int CancelDialog = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin untuk membatalkan?", "Warning", JOptionPane.YES_NO_OPTION);
        if(CancelDialog == JOptionPane.YES_OPTION){
            textFieldHandler.ResetAllText();
            NavigateTo(Destination);
        }
    }
    
    private void LogOutConfirmation(JPanel Destination)
    {
        int CancelDialog = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin untuk keluar?", "Warning", JOptionPane.YES_NO_OPTION);
        if(CancelDialog == JOptionPane.YES_OPTION){
            LoginFrame LoginFrame = new LoginFrame();
            LoginFrame.setVisible(true);
            this.setVisible(false);
        }
    }

    private void LoadComboBoxEvent()
    {
        FormNilaiNama.addItemListener((ItemEvent evt) -> 
        {
            if (evt.getStateChange() == ItemEvent.SELECTED) 
            {
                int rowIndex = _mahasiswaTableHandler.GetRowIndex(Mahasiswa.Nama, FormNilaiNama.getSelectedItem());
                Object value = _mahasiswaTableHandler.GetValueAt(Mahasiswa.Nim, rowIndex);
                FormNilaiNim.setText(value.toString());
            }
        });
        
        FormNilaiNamaMataKuliah.addItemListener((ItemEvent evt) -> 
        {
            if (evt.getStateChange() == ItemEvent.SELECTED) 
            {
                int rowIndex = _mataKuliahTableHandler.GetRowIndex(MataKuliah.Nama, FormNilaiNamaMataKuliah.getSelectedItem());
                Object value = _mataKuliahTableHandler.GetValueAt(MataKuliah.No, rowIndex);
                FormNilaiKodeMataKuliah.setText(value.toString());
            }
        });
        
        FormSimulasiAkhirNamaMataKuliah.addItemListener((ItemEvent evt) -> 
        {
            if (evt.getStateChange() == ItemEvent.SELECTED) 
            {
                int rowIndex = _mataKuliahTableHandler.GetRowIndex(MataKuliah.Nama, FormSimulasiAkhirNamaMataKuliah.getSelectedItem());
                Object value = _mataKuliahTableHandler.GetValueAt(MataKuliah.No, rowIndex);
                FormSimulasiAkhirKodeMataKuliah.setText(value.toString());
            }
        });
        
        FormSimulasiKasusOperator.addItemListener((ItemEvent evt) -> 
        {
            if (evt.getStateChange() == ItemEvent.SELECTED) 
            {
                LoadNominalComboBox();
            }
        });
    }
        
    /**
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jCalendar2 = new com.toedter.calendar.JCalendar();
        PanelMenu = new javax.swing.JPanel();
        ButtonHomePage = new javax.swing.JButton();
        ButtonDataMahasiswa = new javax.swing.JButton();
        ButtonDataMatkul = new javax.swing.JButton();
        ButtonDataNilai = new javax.swing.JButton();
        ButtonSimulasiAkhir = new javax.swing.JButton();
        ButtonSimulasiKasus = new javax.swing.JButton();
        ButtonDataMahasiswa1 = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        PanelContent = new javax.swing.JPanel();
        PanelHomepage = new javax.swing.JPanel();
        LabelTitleHome = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        PanelMahasiswa = new javax.swing.JPanel();
        LabelTitleMahasiswa = new javax.swing.JLabel();
        FormSearchMahasiswa = new javax.swing.JTextField();
        ButtonMahasiswaHapus = new javax.swing.JButton();
        ButtonMahasiswaEdit = new javax.swing.JButton();
        ButtonMahasiswaTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableMahasiswa = new javax.swing.JTable();
        ButtonMahasiswaCari = new javax.swing.JButton();
        PanelMataKuliah = new javax.swing.JPanel();
        LabelTitleMataKuliah = new javax.swing.JLabel();
        SearchMatkul = new javax.swing.JTextField();
        ButtonSearchMatkul = new javax.swing.JButton();
        ButtonMatkulHapus = new javax.swing.JButton();
        ButtonMatkulEdit = new javax.swing.JButton();
        ButtonMatkulTambah = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableMatkul = new javax.swing.JTable();
        PanelDataNilai = new javax.swing.JPanel();
        LabelTitleNilai = new javax.swing.JLabel();
        SearchNilai = new javax.swing.JTextField();
        ButtonSearchNilai = new javax.swing.JButton();
        ButtonNilaiHapus = new javax.swing.JButton();
        ButtonNilaiEdit = new javax.swing.JButton();
        ButtonNilaiTambah = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableNilai = new javax.swing.JTable();
        PanelSimulasiAkhir = new javax.swing.JPanel();
        LabelTitleSimulasiAkhir = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableSimulasiAkhir = new javax.swing.JTable();
        ButtonSimulasiAkhirHapus = new javax.swing.JButton();
        ButtonSimulasiAkhirEdit = new javax.swing.JButton();
        ButtonSimulasiAkhirTambah = new javax.swing.JButton();
        PanelSimulasiKasus = new javax.swing.JPanel();
        LabelTitleSimulasiKasus = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableSimulasiKasus = new javax.swing.JTable();
        ButtonSimulasiKasusHapus = new javax.swing.JButton();
        ButtonSimulasiKasusEdit = new javax.swing.JButton();
        ButtonSimulasiKasusTambah = new javax.swing.JButton();
        FieldSimulasiKasusPencarian = new javax.swing.JTextField();
        ButtonSimulasiKasusCari = new javax.swing.JButton();
        PanelAddMahasiswa = new javax.swing.JPanel();
        LabelTitleAddMahasiswa = new javax.swing.JLabel();
        ButtonMahasiswaSimpan = new javax.swing.JButton();
        ButtonMahasiswaCancel = new javax.swing.JButton();
        MahasiswaLayoutKiri = new javax.swing.JPanel();
        FromMahasiswaNIM = new javax.swing.JTextField();
        LabelMahasiswaNIM = new javax.swing.JLabel();
        FromMahasiswaNama = new javax.swing.JTextField();
        LabelMahasiswaNama = new javax.swing.JLabel();
        LabelMahasiswaTempatLahir = new javax.swing.JLabel();
        FromMahasiswaTempatLahir = new javax.swing.JTextField();
        LabelMahasiswaTanggalLahir = new javax.swing.JLabel();
        FromMahasiswaTanggalLahir = new javax.swing.JFormattedTextField();
        FormChooserTanggalLahir = new com.toedter.calendar.JDateChooser();
        MahasiswaLayoutKiri1 = new javax.swing.JPanel();
        LabelMahasiswaAlamat = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        FromMahasiswaAlamat = new javax.swing.JTextArea();
        PanelAddMataKuliah = new javax.swing.JPanel();
        LabelTitleAddMataKuliah = new javax.swing.JLabel();
        ButtonMataKuliahCancel = new javax.swing.JButton();
        ButtonMataKuliahSimpan = new javax.swing.JButton();
        infoMataKuliah = new javax.swing.JPanel();
        FromMataKuliahNomor = new javax.swing.JTextField();
        LabelFromMataKuliahNomor = new javax.swing.JLabel();
        FromMataKuliahNama = new javax.swing.JTextField();
        LabelFromMataKuliahNama = new javax.swing.JLabel();
        PanelAddNilai = new javax.swing.JPanel();
        LabelTitleAddNilai = new javax.swing.JLabel();
        ButtonNilaiCancel = new javax.swing.JButton();
        ButtonNilaiSimpan = new javax.swing.JButton();
        informasiMahasiwa = new javax.swing.JPanel();
        LabelNilaiNama = new javax.swing.JLabel();
        FormNilaiNama = new javax.swing.JComboBox<>();
        LabelNilaiNIM = new javax.swing.JLabel();
        FormNilaiNim = new javax.swing.JTextField();
        informasi_mata_kuliah = new javax.swing.JPanel();
        FormNilaiNamaMataKuliah = new javax.swing.JComboBox<>();
        LabelNilaiNamaMataKuliah = new javax.swing.JLabel();
        LabelNilaiKodeMataKuliah = new javax.swing.JLabel();
        FormNilaiKodeMataKuliah = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        LabelNilaiKehadiran = new javax.swing.JLabel();
        FormNilaiKehadiran = new javax.swing.JTextField();
        LabelNilaiTugas1 = new javax.swing.JLabel();
        FormNilaiTugas1 = new javax.swing.JTextField();
        LabelNilaiPertemuan = new javax.swing.JLabel();
        LabelNilaiTugas2 = new javax.swing.JLabel();
        FormNilaiTugas2 = new javax.swing.JTextField();
        LabelNilaiUTS = new javax.swing.JLabel();
        FormNilaiUTS = new javax.swing.JTextField();
        LabelNilaiUAS = new javax.swing.JLabel();
        FormNilaiUAS = new javax.swing.JTextField();
        LabelNilaiAngkatan = new javax.swing.JLabel();
        FormNilaiAngkatan = new javax.swing.JFormattedTextField();
        LabelNilaiTugas3 = new javax.swing.JLabel();
        FormNilaiTugas3 = new javax.swing.JTextField();
        PanelAddSimulasiAkhir = new javax.swing.JPanel();
        LabelTitleAddSimulasiAkhir = new javax.swing.JLabel();
        ButtonSimulasiAkhirCancel = new javax.swing.JButton();
        ButtonSimulasiAkhirSimpan = new javax.swing.JButton();
        LayoutPresensi = new javax.swing.JPanel();
        LabelSimulasiAkhirPresentaseAbsen1 = new javax.swing.JLabel();
        FormSimulasiAkhirPresentaseTugas = new javax.swing.JTextField();
        FormSimulasiAkhirPresentaseAbsen = new javax.swing.JTextField();
        LabelSimulasiAkhirPresentaseAbsen = new javax.swing.JLabel();
        LabelSimulasiAkhirPresentaseUTS = new javax.swing.JLabel();
        FormSimulasiAkhirPresentaseUTS = new javax.swing.JTextField();
        LabelSimulasiAkhirPresentaseUAS = new javax.swing.JLabel();
        FormSimulasiAkhirPresentaseUAS = new javax.swing.JTextField();
        LayoutNilai = new javax.swing.JPanel();
        FormSimulasiAkhirUAS = new javax.swing.JTextField();
        LabelSimulasiAkhirUAS = new javax.swing.JLabel();
        FormSimulasiAkhirUTS = new javax.swing.JTextField();
        LabelSimulasiAkhirUTS = new javax.swing.JLabel();
        FormSimulasiAkhirTugas3 = new javax.swing.JTextField();
        LabelSimulasiAkhirTugas3 = new javax.swing.JLabel();
        FormSimulasiAkhirTugas2 = new javax.swing.JTextField();
        LabelSimulasiAkhirTugas2 = new javax.swing.JLabel();
        FormSimulasiAkhirTugas1 = new javax.swing.JTextField();
        LabelSimulasiAkhirTugas1 = new javax.swing.JLabel();
        FormSimulasiAkhirKehadiran = new javax.swing.JTextField();
        LabelSimulasiAkhirKehadiran = new javax.swing.JLabel();
        LabelSimulasiAkhirPertemuan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        LabelSimulasiAkhirNamaMataKuliah = new javax.swing.JLabel();
        FormSimulasiAkhirNamaMataKuliah = new javax.swing.JComboBox<>();
        LabelSimulasiAkhirKodeMataKuliah = new javax.swing.JLabel();
        FormSimulasiAkhirKodeMataKuliah = new javax.swing.JTextField();
        PanelAddSimulasiKasus = new javax.swing.JPanel();
        LabelTitleAddSimulasiKasus = new javax.swing.JLabel();
        ButtonCancelSimulasiKasus = new javax.swing.JButton();
        ButtonSimpanSimulasiKasus = new javax.swing.JButton();
        LayoutSimulasiKasusKiri = new javax.swing.JPanel();
        LabelSimulasiKasusOperator = new javax.swing.JLabel();
        FormSimulasiKasusOperator = new javax.swing.JComboBox<>();
        LabelSimulasiKasusNoTelp = new javax.swing.JLabel();
        FormSimulasiKasusNoTelp = new javax.swing.JTextField();
        LabelSimulasiKasusNominal = new javax.swing.JLabel();
        FormSimulasiKasusNominal = new javax.swing.JComboBox<>();
        LayoutSimulasiKasusKanan = new javax.swing.JPanel();
        LabelSimulasiKasusMetodePembayaran = new javax.swing.JLabel();
        FormSimulasiKasusMetodePembayaran = new javax.swing.JComboBox<>();
        FormSimulasiKasusStatus = new javax.swing.JComboBox<>();
        LabelSimulasiKasusStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(244, 245, 246));
        setPreferredSize(new java.awt.Dimension(970, 655));
        setResizable(false);

        PanelMenu.setBackground(new java.awt.Color(255, 255, 255));

        ButtonHomePage.setText("Home Page");
        ButtonHomePage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonHomePageActionPerformed(evt);
            }
        });

        ButtonDataMahasiswa.setText("Mahasiswa");
        ButtonDataMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataMahasiswaActionPerformed(evt);
            }
        });

        ButtonDataMatkul.setText("Mata Kuliah");
        ButtonDataMatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataMatkulActionPerformed(evt);
            }
        });

        ButtonDataNilai.setText("Nilai Mahasiswa");
        ButtonDataNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataNilaiActionPerformed(evt);
            }
        });

        ButtonSimulasiAkhir.setText("Simulasi Nilai");
        ButtonSimulasiAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiAkhirActionPerformed(evt);
            }
        });

        ButtonSimulasiKasus.setText("Simulasi Kasus");
        ButtonSimulasiKasus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiKasusActionPerformed(evt);
            }
        });

        ButtonDataMahasiswa1.setText("Keluar");
        ButtonDataMahasiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataMahasiswa1ActionPerformed(evt);
            }
        });

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo 1.png"))); // NOI18N

        javax.swing.GroupLayout PanelMenuLayout = new javax.swing.GroupLayout(PanelMenu);
        PanelMenu.setLayout(PanelMenuLayout);
        PanelMenuLayout.setHorizontalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonHomePage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonDataMatkul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonDataNilai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonSimulasiAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonSimulasiKasus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonDataMahasiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonDataMahasiswa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(logo)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        PanelMenuLayout.setVerticalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(logo)
                .addGap(34, 34, 34)
                .addComponent(ButtonHomePage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDataMahasiswa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDataMatkul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDataNilai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonSimulasiAkhir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonSimulasiKasus)
                .addGap(28, 28, 28)
                .addComponent(ButtonDataMahasiswa1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelContent.setBackground(new java.awt.Color(244, 245, 246));
        PanelContent.setToolTipText("");
        PanelContent.setLayout(new java.awt.CardLayout());

        PanelHomepage.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleHome.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleHome.setText("Home Page");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/doni.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel4.setText("10119907");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel5.setText("T Doni Indraprasta");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/aris.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel7.setText("Aris Prabowo");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel8.setText("10119914");

        javax.swing.GroupLayout PanelHomepageLayout = new javax.swing.GroupLayout(PanelHomepage);
        PanelHomepage.setLayout(PanelHomepageLayout);
        PanelHomepageLayout.setHorizontalGroup(
            PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHomepageLayout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(LabelTitleHome)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelHomepageLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelHomepageLayout.createSequentialGroup()
                        .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelHomepageLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(118, 118, 118)
                                .addComponent(jLabel6))
                            .addGroup(PanelHomepageLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel5)
                                .addGap(195, 195, 195)
                                .addComponent(jLabel7)))
                        .addContainerGap(167, Short.MAX_VALUE))
                    .addGroup(PanelHomepageLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(225, 225, 225))))
        );
        PanelHomepageLayout.setVerticalGroup(
            PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHomepageLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(LabelTitleHome)
                .addGap(60, 60, 60)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        PanelContent.add(PanelHomepage, "card2");

        PanelMahasiswa.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleMahasiswa.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleMahasiswa.setText("Mahasiswa");

        FormSearchMahasiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSearchMahasiswaKeyTyped(evt);
            }
        });

        ButtonMahasiswaHapus.setText("Hapus");
        ButtonMahasiswaHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaHapusActionPerformed(evt);
            }
        });

        ButtonMahasiswaEdit.setText("Ubah");
        ButtonMahasiswaEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaEditActionPerformed(evt);
            }
        });

        ButtonMahasiswaTambah.setText("Tambah");
        ButtonMahasiswaTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaTambahActionPerformed(evt);
            }
        });

        TableMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nim", "Nama", "Tempat Lahir", "Tanggal Lahir", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableMahasiswa);

        ButtonMahasiswaCari.setText("Cari");
        ButtonMahasiswaCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelMahasiswaLayout = new javax.swing.GroupLayout(PanelMahasiswa);
        PanelMahasiswa.setLayout(PanelMahasiswaLayout);
        PanelMahasiswaLayout.setHorizontalGroup(
            PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMahasiswaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTitleMahasiswa)
                    .addGroup(PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelMahasiswaLayout.createSequentialGroup()
                            .addComponent(FormSearchMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonMahasiswaCari)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonMahasiswaHapus)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonMahasiswaEdit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonMahasiswaTambah))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        PanelMahasiswaLayout.setVerticalGroup(
            PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMahasiswaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleMahasiswa)
                .addGap(24, 24, 24)
                .addGroup(PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FormSearchMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonMahasiswaHapus)
                    .addComponent(ButtonMahasiswaEdit)
                    .addComponent(ButtonMahasiswaTambah)
                    .addComponent(ButtonMahasiswaCari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        PanelContent.add(PanelMahasiswa, "card3");

        PanelMataKuliah.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleMataKuliah.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleMataKuliah.setText("Mata Kuliah");

        SearchMatkul.setToolTipText("");

        ButtonSearchMatkul.setText("Cari");
        ButtonSearchMatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSearchMatkulActionPerformed(evt);
            }
        });

        ButtonMatkulHapus.setText("Hapus");
        ButtonMatkulHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMatkulHapusActionPerformed(evt);
            }
        });

        ButtonMatkulEdit.setText("Ubah");
        ButtonMatkulEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMatkulEditActionPerformed(evt);
            }
        });

        ButtonMatkulTambah.setText("Tambah");
        ButtonMatkulTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMatkulTambahActionPerformed(evt);
            }
        });

        TableMatkul.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor MK", "Nama MK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TableMatkul);

        javax.swing.GroupLayout PanelMataKuliahLayout = new javax.swing.GroupLayout(PanelMataKuliah);
        PanelMataKuliah.setLayout(PanelMataKuliahLayout);
        PanelMataKuliahLayout.setHorizontalGroup(
            PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMataKuliahLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelTitleMataKuliah)
                    .addGroup(PanelMataKuliahLayout.createSequentialGroup()
                        .addComponent(SearchMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearchMatkul)
                        .addGap(164, 164, 164)
                        .addComponent(ButtonMatkulHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMatkulEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMatkulTambah))
                    .addComponent(jScrollPane2))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        PanelMataKuliahLayout.setVerticalGroup(
            PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMataKuliahLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleMataKuliah)
                .addGap(24, 24, 24)
                .addGroup(PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSearchMatkul)
                    .addComponent(ButtonMatkulHapus)
                    .addComponent(ButtonMatkulEdit)
                    .addComponent(ButtonMatkulTambah))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelContent.add(PanelMataKuliah, "card4");

        PanelDataNilai.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleNilai.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleNilai.setText("Nilai Mahasiswa");

        ButtonSearchNilai.setText("Cari");
        ButtonSearchNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSearchNilaiActionPerformed(evt);
            }
        });

        ButtonNilaiHapus.setText("Hapus");
        ButtonNilaiHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNilaiHapusActionPerformed(evt);
            }
        });

        ButtonNilaiEdit.setText("Ubah");
        ButtonNilaiEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNilaiEditActionPerformed(evt);
            }
        });

        ButtonNilaiTambah.setText("Tambah");
        ButtonNilaiTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNilaiTambahActionPerformed(evt);
            }
        });

        TableNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nama", "Mata Kuliah", "Absensi", "Tgs1", "Tgs2", "Tgs3", "UTS", "UAS", "Nilai Absen", "Nilai Tugas", "Nilai UTS", "Nilai UAS", "Nilai Akhir", "Indeks", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TableNilai);

        javax.swing.GroupLayout PanelDataNilaiLayout = new javax.swing.GroupLayout(PanelDataNilai);
        PanelDataNilai.setLayout(PanelDataNilaiLayout);
        PanelDataNilaiLayout.setHorizontalGroup(
            PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDataNilaiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTitleNilai)
                    .addGroup(PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelDataNilaiLayout.createSequentialGroup()
                            .addComponent(SearchNilai, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonSearchNilai)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonNilaiHapus)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonNilaiEdit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonNilaiTambah))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        PanelDataNilaiLayout.setVerticalGroup(
            PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDataNilaiLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleNilai)
                .addGap(24, 24, 24)
                .addGroup(PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSearchNilai)
                    .addComponent(ButtonNilaiHapus)
                    .addComponent(ButtonNilaiEdit)
                    .addComponent(ButtonNilaiTambah))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelContent.add(PanelDataNilai, "card5");

        PanelSimulasiAkhir.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleSimulasiAkhir.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleSimulasiAkhir.setText("Simulasi Nilai");

        TableSimulasiAkhir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nama MK", "Absen (%)", "Tugas (%)", "UTS (%)", "UAS (%)", "Absensi", "Tgs1", "Tgs2", "Tgs3", "UTS", "UAS", "Nilai Absen", "Nilai Tugas", "Niali UTS", "Nilai UAS", "Nilai Akhir", "Indeks", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TableSimulasiAkhir);
        if (TableSimulasiAkhir.getColumnModel().getColumnCount() > 0) {
            TableSimulasiAkhir.getColumnModel().getColumn(1).setHeaderValue("Nama MK");
            TableSimulasiAkhir.getColumnModel().getColumn(2).setHeaderValue("Absen (%)");
            TableSimulasiAkhir.getColumnModel().getColumn(3).setHeaderValue("Tugas (%)");
            TableSimulasiAkhir.getColumnModel().getColumn(4).setHeaderValue("UTS (%)");
            TableSimulasiAkhir.getColumnModel().getColumn(5).setHeaderValue("UAS (%)");
            TableSimulasiAkhir.getColumnModel().getColumn(6).setHeaderValue("Absensi");
            TableSimulasiAkhir.getColumnModel().getColumn(7).setHeaderValue("Tgs1");
            TableSimulasiAkhir.getColumnModel().getColumn(8).setHeaderValue("Tgs2");
            TableSimulasiAkhir.getColumnModel().getColumn(9).setHeaderValue("Tgs3");
            TableSimulasiAkhir.getColumnModel().getColumn(10).setHeaderValue("UTS");
            TableSimulasiAkhir.getColumnModel().getColumn(11).setHeaderValue("UAS");
            TableSimulasiAkhir.getColumnModel().getColumn(12).setHeaderValue("Nilai Absen");
            TableSimulasiAkhir.getColumnModel().getColumn(13).setHeaderValue("Nilai Tugas");
            TableSimulasiAkhir.getColumnModel().getColumn(14).setHeaderValue("Niali UTS");
            TableSimulasiAkhir.getColumnModel().getColumn(15).setHeaderValue("Nilai UAS");
            TableSimulasiAkhir.getColumnModel().getColumn(16).setHeaderValue("Nilai Akhir");
            TableSimulasiAkhir.getColumnModel().getColumn(17).setHeaderValue("Indeks");
        }

        ButtonSimulasiAkhirHapus.setText("Hapus");
        ButtonSimulasiAkhirHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiAkhirHapusActionPerformed(evt);
            }
        });

        ButtonSimulasiAkhirEdit.setText("Ubah");
        ButtonSimulasiAkhirEdit.setActionCommand("");
        ButtonSimulasiAkhirEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiAkhirEditActionPerformed(evt);
            }
        });

        ButtonSimulasiAkhirTambah.setText("Tambah");
        ButtonSimulasiAkhirTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiAkhirTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelSimulasiAkhirLayout = new javax.swing.GroupLayout(PanelSimulasiAkhir);
        PanelSimulasiAkhir.setLayout(PanelSimulasiAkhirLayout);
        PanelSimulasiAkhirLayout.setHorizontalGroup(
            PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiAkhirLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(ButtonSimulasiAkhirHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiAkhirEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiAkhirTambah))
                    .addGroup(PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelTitleSimulasiAkhir)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        PanelSimulasiAkhirLayout.setVerticalGroup(
            PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiAkhirLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleSimulasiAkhir)
                .addGap(24, 24, 24)
                .addGroup(PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSimulasiAkhirHapus)
                    .addComponent(ButtonSimulasiAkhirEdit)
                    .addComponent(ButtonSimulasiAkhirTambah))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelContent.add(PanelSimulasiAkhir, "card6");

        PanelSimulasiKasus.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleSimulasiKasus.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleSimulasiKasus.setText("Simulasi Kasus");

        TableSimulasiKasus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Transaksi", "No. Telp", "Operator", "Nominal", "Metode Bayar", "Tanggal", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(TableSimulasiKasus);

        ButtonSimulasiKasusHapus.setText("Hapus");
        ButtonSimulasiKasusHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiKasusHapusActionPerformed(evt);
            }
        });

        ButtonSimulasiKasusEdit.setText("Ubah");
        ButtonSimulasiKasusEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiKasusEditActionPerformed(evt);
            }
        });

        ButtonSimulasiKasusTambah.setText("Tambah");
        ButtonSimulasiKasusTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiKasusTambahActionPerformed(evt);
            }
        });

        ButtonSimulasiKasusCari.setText("Cari");
        ButtonSimulasiKasusCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiKasusCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelSimulasiKasusLayout = new javax.swing.GroupLayout(PanelSimulasiKasus);
        PanelSimulasiKasus.setLayout(PanelSimulasiKasusLayout);
        PanelSimulasiKasusLayout.setHorizontalGroup(
            PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiKasusLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSimulasiKasusLayout.createSequentialGroup()
                        .addComponent(FieldSimulasiKasusPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiKasusCari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonSimulasiKasusHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiKasusEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiKasusTambah))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTitleSimulasiKasus))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        PanelSimulasiKasusLayout.setVerticalGroup(
            PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiKasusLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleSimulasiKasus)
                .addGroup(PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSimulasiKasusLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ButtonSimulasiKasusHapus)
                            .addComponent(ButtonSimulasiKasusEdit)
                            .addComponent(ButtonSimulasiKasusTambah)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSimulasiKasusLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FieldSimulasiKasusPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonSimulasiKasusCari))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        PanelContent.add(PanelSimulasiKasus, "card7");

        PanelAddMahasiswa.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddMahasiswa.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddMahasiswa.setText("Tambah Mahasiswa");

        ButtonMahasiswaSimpan.setText("Simpan");
        ButtonMahasiswaSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaSimpanActionPerformed(evt);
            }
        });

        ButtonMahasiswaCancel.setText("Cancel");
        ButtonMahasiswaCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaCancelActionPerformed(evt);
            }
        });

        MahasiswaLayoutKiri.setBackground(new java.awt.Color(244, 245, 246));
        MahasiswaLayoutKiri.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi Personal"));

        FromMahasiswaNIM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FromMahasiswaNIMKeyTyped(evt);
            }
        });

        LabelMahasiswaNIM.setText("NIM");

        LabelMahasiswaNama.setText("Nama");

        LabelMahasiswaTempatLahir.setText("Tempat Lahir");

        LabelMahasiswaTanggalLahir.setText("Tanggal Lahir");

        FromMahasiswaTanggalLahir.setEditable(false);

        FormChooserTanggalLahir.setDateFormatString("MMMM d, y");
        FormChooserTanggalLahir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FormChooserTanggalLahirPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout MahasiswaLayoutKiriLayout = new javax.swing.GroupLayout(MahasiswaLayoutKiri);
        MahasiswaLayoutKiri.setLayout(MahasiswaLayoutKiriLayout);
        MahasiswaLayoutKiriLayout.setHorizontalGroup(
            MahasiswaLayoutKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MahasiswaLayoutKiriLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(MahasiswaLayoutKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(MahasiswaLayoutKiriLayout.createSequentialGroup()
                        .addGroup(MahasiswaLayoutKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelMahasiswaTanggalLahir)
                            .addComponent(FromMahasiswaTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormChooserTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelMahasiswaTempatLahir)
                    .addComponent(LabelMahasiswaNama)
                    .addComponent(LabelMahasiswaNIM)
                    .addComponent(FromMahasiswaTempatLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(FromMahasiswaNama)
                    .addComponent(FromMahasiswaNIM))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        MahasiswaLayoutKiriLayout.setVerticalGroup(
            MahasiswaLayoutKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MahasiswaLayoutKiriLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(LabelMahasiswaNIM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMahasiswaNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelMahasiswaNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMahasiswaNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelMahasiswaTempatLahir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMahasiswaTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelMahasiswaTanggalLahir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MahasiswaLayoutKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FromMahasiswaTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FormChooserTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        MahasiswaLayoutKiri1.setBackground(new java.awt.Color(244, 245, 246));
        MahasiswaLayoutKiri1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi Tempat Tinggal"));

        LabelMahasiswaAlamat.setText("Alamat");

        FromMahasiswaAlamat.setColumns(20);
        FromMahasiswaAlamat.setRows(5);
        jScrollPane5.setViewportView(FromMahasiswaAlamat);

        javax.swing.GroupLayout MahasiswaLayoutKiri1Layout = new javax.swing.GroupLayout(MahasiswaLayoutKiri1);
        MahasiswaLayoutKiri1.setLayout(MahasiswaLayoutKiri1Layout);
        MahasiswaLayoutKiri1Layout.setHorizontalGroup(
            MahasiswaLayoutKiri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MahasiswaLayoutKiri1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(MahasiswaLayoutKiri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelMahasiswaAlamat)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        MahasiswaLayoutKiri1Layout.setVerticalGroup(
            MahasiswaLayoutKiri1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MahasiswaLayoutKiri1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(LabelMahasiswaAlamat)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelAddMahasiswaLayout = new javax.swing.GroupLayout(PanelAddMahasiswa);
        PanelAddMahasiswa.setLayout(PanelAddMahasiswaLayout);
        PanelAddMahasiswaLayout.setHorizontalGroup(
            PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                        .addComponent(ButtonMahasiswaCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMahasiswaSimpan))
                    .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(LabelTitleAddMahasiswa)
                        .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                            .addComponent(MahasiswaLayoutKiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(MahasiswaLayoutKiri1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        PanelAddMahasiswaLayout.setVerticalGroup(
            PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleAddMahasiswa)
                .addGap(24, 24, 24)
                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MahasiswaLayoutKiri1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MahasiswaLayoutKiri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonMahasiswaSimpan)
                    .addComponent(ButtonMahasiswaCancel))
                .addContainerGap(280, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddMahasiswa, "card8");

        PanelAddMataKuliah.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddMataKuliah.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddMataKuliah.setText("Tambah Mata Kuliah");

        ButtonMataKuliahCancel.setText("Cancel");
        ButtonMataKuliahCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMataKuliahCancelActionPerformed(evt);
            }
        });

        ButtonMataKuliahSimpan.setText("Simpan");
        ButtonMataKuliahSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMataKuliahSimpanActionPerformed(evt);
            }
        });

        infoMataKuliah.setBackground(new java.awt.Color(244, 245, 246));
        infoMataKuliah.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi Mata Kuliah"));

        LabelFromMataKuliahNomor.setText("Nomor Mata Kuliah");

        LabelFromMataKuliahNama.setText("Nama Mata Kuliah");

        javax.swing.GroupLayout infoMataKuliahLayout = new javax.swing.GroupLayout(infoMataKuliah);
        infoMataKuliah.setLayout(infoMataKuliahLayout);
        infoMataKuliahLayout.setHorizontalGroup(
            infoMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoMataKuliahLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(infoMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FromMataKuliahNama, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelFromMataKuliahNama)
                    .addComponent(LabelFromMataKuliahNomor)
                    .addComponent(FromMataKuliahNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        infoMataKuliahLayout.setVerticalGroup(
            infoMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoMataKuliahLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(LabelFromMataKuliahNomor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMataKuliahNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelFromMataKuliahNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMataKuliahNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelAddMataKuliahLayout = new javax.swing.GroupLayout(PanelAddMataKuliah);
        PanelAddMataKuliah.setLayout(PanelAddMataKuliahLayout);
        PanelAddMataKuliahLayout.setHorizontalGroup(
            PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                        .addComponent(LabelTitleAddMataKuliah)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                        .addGroup(PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(infoMataKuliah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                                .addComponent(ButtonMataKuliahCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ButtonMataKuliahSimpan)))
                        .addGap(38, 38, 38))))
        );
        PanelAddMataKuliahLayout.setVerticalGroup(
            PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleAddMataKuliah)
                .addGap(24, 24, 24)
                .addComponent(infoMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonMataKuliahCancel)
                    .addComponent(ButtonMataKuliahSimpan))
                .addContainerGap(371, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddMataKuliah, "card9");

        PanelAddNilai.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddNilai.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddNilai.setText("Tambah Nilai");

        ButtonNilaiCancel.setText("Cancel");
        ButtonNilaiCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNilaiCancelActionPerformed(evt);
            }
        });

        ButtonNilaiSimpan.setText("Simpan");
        ButtonNilaiSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNilaiSimpanActionPerformed(evt);
            }
        });

        informasiMahasiwa.setBackground(new java.awt.Color(244, 245, 246));
        informasiMahasiwa.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi Mahasiswa"));

        LabelNilaiNama.setText("Nama");

        LabelNilaiNIM.setText("Nim");

        FormNilaiNim.setEditable(false);

        javax.swing.GroupLayout informasiMahasiwaLayout = new javax.swing.GroupLayout(informasiMahasiwa);
        informasiMahasiwa.setLayout(informasiMahasiwaLayout);
        informasiMahasiwaLayout.setHorizontalGroup(
            informasiMahasiwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informasiMahasiwaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(informasiMahasiwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FormNilaiNim, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNilaiNIM)
                    .addComponent(LabelNilaiNama)
                    .addComponent(FormNilaiNama, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        informasiMahasiwaLayout.setVerticalGroup(
            informasiMahasiwaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informasiMahasiwaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(LabelNilaiNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormNilaiNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelNilaiNIM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormNilaiNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        informasi_mata_kuliah.setBackground(new java.awt.Color(244, 245, 246));
        informasi_mata_kuliah.setBorder(javax.swing.BorderFactory.createTitledBorder("Mata Kuliah"));

        FormNilaiNamaMataKuliah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormNilaiNamaMataKuliahActionPerformed(evt);
            }
        });

        LabelNilaiNamaMataKuliah.setText("Nama Mata Kuliah");

        LabelNilaiKodeMataKuliah.setText("Kode Mata Kuliah");

        FormNilaiKodeMataKuliah.setEditable(false);

        javax.swing.GroupLayout informasi_mata_kuliahLayout = new javax.swing.GroupLayout(informasi_mata_kuliah);
        informasi_mata_kuliah.setLayout(informasi_mata_kuliahLayout);
        informasi_mata_kuliahLayout.setHorizontalGroup(
            informasi_mata_kuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informasi_mata_kuliahLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(informasi_mata_kuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNilaiKodeMataKuliah)
                    .addComponent(FormNilaiKodeMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNilaiNamaMataKuliah)
                    .addComponent(FormNilaiNamaMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        informasi_mata_kuliahLayout.setVerticalGroup(
            informasi_mata_kuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informasi_mata_kuliahLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(LabelNilaiNamaMataKuliah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormNilaiNamaMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelNilaiKodeMataKuliah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormNilaiKodeMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(244, 245, 246));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Penilaian"));

        LabelNilaiKehadiran.setText("Kehadiran");

        FormNilaiKehadiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormNilaiKehadiranKeyTyped(evt);
            }
        });

        LabelNilaiTugas1.setText("Tugas 1");

        FormNilaiTugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormNilaiTugas1KeyTyped(evt);
            }
        });

        LabelNilaiPertemuan.setText("Pertemuan");

        LabelNilaiTugas2.setText("Tugas 2");

        FormNilaiTugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormNilaiTugas2ActionPerformed(evt);
            }
        });
        FormNilaiTugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormNilaiTugas2KeyTyped(evt);
            }
        });

        LabelNilaiUTS.setText("UTS");

        FormNilaiUTS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormNilaiUTSKeyTyped(evt);
            }
        });

        LabelNilaiUAS.setText("UAS");

        FormNilaiUAS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormNilaiUASKeyTyped(evt);
            }
        });

        LabelNilaiAngkatan.setText("Angkatan");

        FormNilaiAngkatan.setEditable(false);
        FormNilaiAngkatan.setText(String.valueOf(DateHelper.GetCurrentYear()));

        LabelNilaiTugas3.setText("Tugas 3");

        FormNilaiTugas3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormNilaiTugas3KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNilaiTugas3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(FormNilaiTugas3)
                            .addComponent(LabelNilaiTugas2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FormNilaiTugas2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelNilaiTugas1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(LabelNilaiKehadiran)
                                        .addGap(0, 197, Short.MAX_VALUE))
                                    .addComponent(FormNilaiKehadiran))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LabelNilaiPertemuan))
                            .addComponent(FormNilaiTugas1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelNilaiUTS)
                            .addComponent(FormNilaiUTS)
                            .addComponent(LabelNilaiUAS)
                            .addComponent(FormNilaiUAS)
                            .addComponent(LabelNilaiAngkatan)
                            .addComponent(FormNilaiAngkatan, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(LabelNilaiKehadiran)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(FormNilaiKehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelNilaiPertemuan)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(LabelNilaiUTS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FormNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelNilaiTugas1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiTugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LabelNilaiUAS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LabelNilaiTugas2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiTugas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(LabelNilaiAngkatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(LabelNilaiTugas3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormNilaiTugas3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelAddNilaiLayout = new javax.swing.GroupLayout(PanelAddNilai);
        PanelAddNilai.setLayout(PanelAddNilaiLayout);
        PanelAddNilaiLayout.setHorizontalGroup(
            PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelTitleAddNilai)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAddNilaiLayout.createSequentialGroup()
                        .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ButtonNilaiCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ButtonNilaiSimpan))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelAddNilaiLayout.createSequentialGroup()
                                .addComponent(informasiMahasiwa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(informasi_mata_kuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))))
        );
        PanelAddNilaiLayout.setVerticalGroup(
            PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleAddNilai)
                .addGap(24, 24, 24)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(informasi_mata_kuliah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(informasiMahasiwa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonNilaiCancel)
                    .addComponent(ButtonNilaiSimpan))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddNilai, "card10");

        PanelAddSimulasiAkhir.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddSimulasiAkhir.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddSimulasiAkhir.setText("Tambah Simulasi Akhir");

        ButtonSimulasiAkhirCancel.setText("Cancel");
        ButtonSimulasiAkhirCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiAkhirCancelActionPerformed(evt);
            }
        });

        ButtonSimulasiAkhirSimpan.setText("Simpan");
        ButtonSimulasiAkhirSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimulasiAkhirSimpanActionPerformed(evt);
            }
        });

        LayoutPresensi.setBackground(new java.awt.Color(244, 245, 246));
        LayoutPresensi.setBorder(javax.swing.BorderFactory.createTitledBorder("Presentase"));

        LabelSimulasiAkhirPresentaseAbsen1.setText("Presentase Tugas");

        FormSimulasiAkhirPresentaseTugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirPresentaseTugasKeyTyped(evt);
            }
        });

        FormSimulasiAkhirPresentaseAbsen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirPresentaseAbsenKeyTyped(evt);
            }
        });

        LabelSimulasiAkhirPresentaseAbsen.setText("Presentase Absen");

        LabelSimulasiAkhirPresentaseUTS.setText("Presentase UTS");

        FormSimulasiAkhirPresentaseUTS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirPresentaseUTSKeyTyped(evt);
            }
        });

        LabelSimulasiAkhirPresentaseUAS.setText("Presentase UAS");

        FormSimulasiAkhirPresentaseUAS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirPresentaseUASKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout LayoutPresensiLayout = new javax.swing.GroupLayout(LayoutPresensi);
        LayoutPresensi.setLayout(LayoutPresensiLayout);
        LayoutPresensiLayout.setHorizontalGroup(
            LayoutPresensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutPresensiLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(LayoutPresensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelSimulasiAkhirPresentaseAbsen)
                    .addComponent(LabelSimulasiAkhirPresentaseUTS)
                    .addComponent(LabelSimulasiAkhirPresentaseUAS)
                    .addComponent(LabelSimulasiAkhirPresentaseAbsen1)
                    .addComponent(FormSimulasiAkhirPresentaseAbsen)
                    .addComponent(FormSimulasiAkhirPresentaseTugas)
                    .addComponent(FormSimulasiAkhirPresentaseUTS)
                    .addComponent(FormSimulasiAkhirPresentaseUAS, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        LayoutPresensiLayout.setVerticalGroup(
            LayoutPresensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutPresensiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(LabelSimulasiAkhirPresentaseAbsen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirPresentaseAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirPresentaseAbsen1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirPresentaseTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirPresentaseUTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirPresentaseUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirPresentaseUAS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirPresentaseUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        LayoutNilai.setBackground(new java.awt.Color(244, 245, 246));
        LayoutNilai.setBorder(javax.swing.BorderFactory.createTitledBorder("Nilai"));

        FormSimulasiAkhirUAS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirUASKeyTyped(evt);
            }
        });

        LabelSimulasiAkhirUAS.setText("UAS");

        FormSimulasiAkhirUTS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirUTSKeyTyped(evt);
            }
        });

        LabelSimulasiAkhirUTS.setText("UTS");

        FormSimulasiAkhirTugas3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirTugas3KeyTyped(evt);
            }
        });

        LabelSimulasiAkhirTugas3.setText("Tugas 3");

        FormSimulasiAkhirTugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirTugas2KeyTyped(evt);
            }
        });

        LabelSimulasiAkhirTugas2.setText("Tugas 2");

        FormSimulasiAkhirTugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirTugas1KeyTyped(evt);
            }
        });

        LabelSimulasiAkhirTugas1.setText("Tugas 1");

        FormSimulasiAkhirKehadiran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FormSimulasiAkhirKehadiranKeyTyped(evt);
            }
        });

        LabelSimulasiAkhirKehadiran.setText("Kehadiran");

        LabelSimulasiAkhirPertemuan.setText("Pertemuan");

        javax.swing.GroupLayout LayoutNilaiLayout = new javax.swing.GroupLayout(LayoutNilai);
        LayoutNilai.setLayout(LayoutNilaiLayout);
        LayoutNilaiLayout.setHorizontalGroup(
            LayoutNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutNilaiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(LayoutNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(LayoutNilaiLayout.createSequentialGroup()
                        .addGroup(LayoutNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelSimulasiAkhirKehadiran)
                            .addComponent(FormSimulasiAkhirKehadiran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(LabelSimulasiAkhirPertemuan))
                    .addComponent(LabelSimulasiAkhirTugas3)
                    .addComponent(LabelSimulasiAkhirTugas2)
                    .addComponent(LabelSimulasiAkhirTugas1)
                    .addComponent(LabelSimulasiAkhirUTS)
                    .addComponent(LabelSimulasiAkhirUAS)
                    .addComponent(FormSimulasiAkhirTugas1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(FormSimulasiAkhirTugas2)
                    .addComponent(FormSimulasiAkhirTugas3)
                    .addComponent(FormSimulasiAkhirUTS)
                    .addComponent(FormSimulasiAkhirUAS))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        LayoutNilaiLayout.setVerticalGroup(
            LayoutNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutNilaiLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(LabelSimulasiAkhirKehadiran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LayoutNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FormSimulasiAkhirKehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelSimulasiAkhirPertemuan))
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirTugas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirTugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirTugas2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirTugas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelSimulasiAkhirTugas3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirTugas3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirUTS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirUAS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(244, 245, 246));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mata Kuliah"));

        LabelSimulasiAkhirNamaMataKuliah.setText("Nama Mata Kuliah");

        LabelSimulasiAkhirKodeMataKuliah.setText("Kode Mata Kuliah");

        FormSimulasiAkhirKodeMataKuliah.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FormSimulasiAkhirKodeMataKuliah)
                    .addComponent(LabelSimulasiAkhirKodeMataKuliah)
                    .addComponent(LabelSimulasiAkhirNamaMataKuliah)
                    .addComponent(FormSimulasiAkhirNamaMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(LabelSimulasiAkhirNamaMataKuliah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirNamaMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiAkhirKodeMataKuliah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiAkhirKodeMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelAddSimulasiAkhirLayout = new javax.swing.GroupLayout(PanelAddSimulasiAkhir);
        PanelAddSimulasiAkhir.setLayout(PanelAddSimulasiAkhirLayout);
        PanelAddSimulasiAkhirLayout.setHorizontalGroup(
            PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(ButtonSimulasiAkhirCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiAkhirSimpan))
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(LabelTitleAddSimulasiAkhir)
                            .addComponent(LayoutPresensi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(LayoutNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        PanelAddSimulasiAkhirLayout.setVerticalGroup(
            PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleAddSimulasiAkhir)
                .addGap(24, 24, 24)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LayoutNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LayoutPresensi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSimulasiAkhirSimpan)
                    .addComponent(ButtonSimulasiAkhirCancel))
                .addGap(23, 23, 23))
        );

        PanelContent.add(PanelAddSimulasiAkhir, "card11");

        PanelAddSimulasiKasus.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddSimulasiKasus.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddSimulasiKasus.setText("Tambah Simulasi Kasus");

        ButtonCancelSimulasiKasus.setText("Cancel");
        ButtonCancelSimulasiKasus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelSimulasiKasusActionPerformed(evt);
            }
        });

        ButtonSimpanSimulasiKasus.setText("Simpan");
        ButtonSimpanSimulasiKasus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSimpanSimulasiKasusActionPerformed(evt);
            }
        });

        LayoutSimulasiKasusKiri.setBackground(new java.awt.Color(244, 245, 246));
        LayoutSimulasiKasusKiri.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi Pengguna"));

        LabelSimulasiKasusOperator.setText("Operator");

        LabelSimulasiKasusNoTelp.setText("No Telp");

        LabelSimulasiKasusNominal.setText("Nominal");

        javax.swing.GroupLayout LayoutSimulasiKasusKiriLayout = new javax.swing.GroupLayout(LayoutSimulasiKasusKiri);
        LayoutSimulasiKasusKiri.setLayout(LayoutSimulasiKasusKiriLayout);
        LayoutSimulasiKasusKiriLayout.setHorizontalGroup(
            LayoutSimulasiKasusKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutSimulasiKasusKiriLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(LayoutSimulasiKasusKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelSimulasiKasusNominal)
                    .addComponent(FormSimulasiKasusNominal, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FormSimulasiKasusNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelSimulasiKasusNoTelp)
                    .addComponent(FormSimulasiKasusOperator, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelSimulasiKasusOperator))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        LayoutSimulasiKasusKiriLayout.setVerticalGroup(
            LayoutSimulasiKasusKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutSimulasiKasusKiriLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(LabelSimulasiKasusOperator)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiKasusOperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiKasusNominal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiKasusNominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiKasusNoTelp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiKasusNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        LayoutSimulasiKasusKanan.setBackground(new java.awt.Color(244, 245, 246));
        LayoutSimulasiKasusKanan.setBorder(javax.swing.BorderFactory.createTitledBorder("Informasi Pembayaran"));

        LabelSimulasiKasusMetodePembayaran.setText("Metode Pembayaran");

        LabelSimulasiKasusStatus.setText("Status");

        javax.swing.GroupLayout LayoutSimulasiKasusKananLayout = new javax.swing.GroupLayout(LayoutSimulasiKasusKanan);
        LayoutSimulasiKasusKanan.setLayout(LayoutSimulasiKasusKananLayout);
        LayoutSimulasiKasusKananLayout.setHorizontalGroup(
            LayoutSimulasiKasusKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutSimulasiKasusKananLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(LayoutSimulasiKasusKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LayoutSimulasiKasusKananLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelSimulasiKasusStatus))
                    .addComponent(FormSimulasiKasusStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelSimulasiKasusMetodePembayaran)
                    .addComponent(FormSimulasiKasusMetodePembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        LayoutSimulasiKasusKananLayout.setVerticalGroup(
            LayoutSimulasiKasusKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutSimulasiKasusKananLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiKasusMetodePembayaran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiKasusMetodePembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelSimulasiKasusStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormSimulasiKasusStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelAddSimulasiKasusLayout = new javax.swing.GroupLayout(PanelAddSimulasiKasus);
        PanelAddSimulasiKasus.setLayout(PanelAddSimulasiKasusLayout);
        PanelAddSimulasiKasusLayout.setHorizontalGroup(
            PanelAddSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddSimulasiKasusLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelAddSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTitleAddSimulasiKasus)
                    .addGroup(PanelAddSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(PanelAddSimulasiKasusLayout.createSequentialGroup()
                            .addComponent(ButtonCancelSimulasiKasus)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonSimpanSimulasiKasus))
                        .addGroup(PanelAddSimulasiKasusLayout.createSequentialGroup()
                            .addComponent(LayoutSimulasiKasusKiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(LayoutSimulasiKasusKanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        PanelAddSimulasiKasusLayout.setVerticalGroup(
            PanelAddSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAddSimulasiKasusLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LabelTitleAddSimulasiKasus)
                .addGap(24, 24, 24)
                .addGroup(PanelAddSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LayoutSimulasiKasusKanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LayoutSimulasiKasusKiri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(PanelAddSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonCancelSimulasiKasus)
                    .addComponent(ButtonSimpanSimulasiKasus))
                .addContainerGap(306, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddSimulasiKasus, "card12");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(PanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonDataMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataMahasiswaActionPerformed
        NavigateTo(PanelMahasiswa);
    }//GEN-LAST:event_ButtonDataMahasiswaActionPerformed

    private void ButtonDataMatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataMatkulActionPerformed
        NavigateTo(PanelMataKuliah);
    }//GEN-LAST:event_ButtonDataMatkulActionPerformed

    private void ButtonDataNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataNilaiActionPerformed
        LoadComboBoxNilaiMahasiswa();
        NavigateTo(PanelDataNilai); 
    }//GEN-LAST:event_ButtonDataNilaiActionPerformed

    private void ButtonHomePageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHomePageActionPerformed
        NavigateTo(PanelHomepage);
    }//GEN-LAST:event_ButtonHomePageActionPerformed

    private void ButtonSimulasiAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirActionPerformed
        LoadComboBoxSimulasiNilai();
        NavigateTo(PanelSimulasiAkhir);
    }//GEN-LAST:event_ButtonSimulasiAkhirActionPerformed

    private void ButtonSimulasiKasusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiKasusActionPerformed
        NavigateTo(PanelSimulasiKasus);
    }//GEN-LAST:event_ButtonSimulasiKasusActionPerformed

    private void ButtonMahasiswaCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaCancelActionPerformed
        CancelConfirmation(_mahasiswaTextFieldHandler, PanelMahasiswa);
        ButtonDataMahasiswa.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMahasiswaCancelActionPerformed

    private void ButtonSimulasiAkhirSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirSimpanActionPerformed
        if(_simulasiNilaiFormState == FormState.Update)
            UpdateFormButtonCallback(_simulasiNilaiController.Update(), _simulasiNilaiTableHandler, _simulasiNilaiTextFieldHandler, PanelSimulasiAkhir);
        else
            CreateFormButtonCallback(_simulasiNilaiController.Create(), _simulasiNilaiTableHandler, _simulasiNilaiTextFieldHandler, PanelSimulasiAkhir);
    }//GEN-LAST:event_ButtonSimulasiAkhirSimpanActionPerformed

    private void ButtonMahasiswaTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaTambahActionPerformed
        NavigateTo(PanelAddMahasiswa);
        _mahasiswaFormState = FormState.Add;
        FromMahasiswaNIM.setEditable(true);
        LabelTitleAddMahasiswa.setText("Tambah Mahasiswa");
        ButtonDataMahasiswa.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMahasiswaTambahActionPerformed

    private void ButtonMatkulTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMatkulTambahActionPerformed
        NavigateTo(PanelAddMataKuliah);
        _mataKuliahFormState = FormState.Add;
        FromMataKuliahNomor.setEditable(true);
        LabelTitleAddMataKuliah.setText("Tambah Mata Kuliah");
        ButtonDataMatkul.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMatkulTambahActionPerformed

    private void ButtonNilaiTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiTambahActionPerformed
        NavigateTo(PanelAddNilai);
        
        FormNilaiNama.setEnabled(true);
        FormNilaiNamaMataKuliah.setEnabled(true);
        
        int rowIndex = _mahasiswaTableHandler.GetRowIndex(Mahasiswa.Nama, FormNilaiNama.getItemAt(1));
        Object value = _mahasiswaTableHandler.GetValueAt(Mahasiswa.Nim, rowIndex);
        FormNilaiNim.setText(value.toString());
        
        int rowIndexM = _mataKuliahTableHandler.GetRowIndex(MataKuliah.Nama, FormNilaiNamaMataKuliah.getItemAt(1));
        Object valueM = _mataKuliahTableHandler.GetValueAt(MataKuliah.No, rowIndexM);
        FormNilaiKodeMataKuliah.setText(valueM.toString());
        
        FormNilaiAngkatan.setText(String.valueOf(DateHelper.GetCurrentYear()));
        _nilaiMahasiswaFormState = FormState.Add;
        LabelTitleAddNilai.setText("Tambah Nilai Mahasiswa");
        ButtonDataNilai.requestFocusInWindow();
    }//GEN-LAST:event_ButtonNilaiTambahActionPerformed

    private void ButtonSimulasiAkhirTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirTambahActionPerformed
        NavigateTo(PanelAddSimulasiAkhir);
        
        FormSimulasiAkhirNamaMataKuliah.setEnabled(true);
        int rowIndex = _mataKuliahTableHandler.GetRowIndex(MataKuliah.Nama, FormSimulasiAkhirNamaMataKuliah.getItemAt(1));
        Object value = _mataKuliahTableHandler.GetValueAt(MataKuliah.No, rowIndex);
        FormSimulasiAkhirKodeMataKuliah.setText(value.toString());
        
        _simulasiNilaiFormState = FormState.Add;
        LabelTitleAddSimulasiAkhir.setText("Tambah Simulasi Nilai");
        ButtonSimulasiAkhir.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiAkhirTambahActionPerformed

    private void ButtonMahasiswaEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaEditActionPerformed
        UpdateButtonCallback(_mahasiswaTableHandler, _mahasiswaTextFieldHandler, PanelAddMahasiswa, "Mahasiswa");
        _mahasiswaFormState = FormState.Update;
        FromMahasiswaNIM.setEditable(false);
        LabelTitleAddMahasiswa.setText("Ubah Mahasiswa");
        ButtonDataMahasiswa.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMahasiswaEditActionPerformed

    private void ButtonMatkulEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMatkulEditActionPerformed
        UpdateButtonCallback(_mataKuliahTableHandler, _mataKuliahTextFieldHandler, PanelAddMataKuliah, "Mata Kuliah");
        _mataKuliahFormState = FormState.Update;
        FromMataKuliahNomor.setEditable(false);
        LabelTitleAddMataKuliah.setText("Ubah Mata Kuliah");
        ButtonDataMatkul.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMatkulEditActionPerformed
    
    private void ButtonNilaiEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiEditActionPerformed
        UpdateButtonCallback(_nilaiMahasiswaTableHandler, _nilaiMahasiswaTextFieldHandler, PanelAddNilai, "Nilai");
        
        FormNilaiNama.setEnabled(false);
        FormNilaiNamaMataKuliah.setEnabled(false);
        
        int rowIndex = _nilaiMahasiswaTableHandler.GetSelectedRowIndex();
        if(rowIndex == -1)
            return;
        
        Object value = _nilaiMahasiswaTableHandler.GetValueAt(NilaiMahasiswa.NamaMhs, rowIndex);
        Object namaMhs = _mahasiswaTableHandler.GetValueAt(Mahasiswa.Nim, _mahasiswaTableHandler.GetRowIndex(Mahasiswa.Nama, value));
        FormNilaiNama.setSelectedItem(value);
        FormNilaiNim.setText(namaMhs.toString());
        
        Object valueM = _nilaiMahasiswaTableHandler.GetValueAt(NilaiMahasiswa.NamaMk, rowIndex);
        Object kodeMk = _mataKuliahTableHandler.GetValueAt(MataKuliah.No, _mataKuliahTableHandler.GetRowIndex(MataKuliah.Nama, valueM));
        FormNilaiNamaMataKuliah.setSelectedItem(valueM);
        FormNilaiKodeMataKuliah.setText(kodeMk.toString());
        
        FormNilaiAngkatan.setText(String.valueOf(DateHelper.GetCurrentYear()));
        
        _nilaiMahasiswaFormState = FormState.Update;
        LabelTitleAddNilai.setText("Ubah Nilai Mahasiswa");
        ButtonDataNilai.requestFocusInWindow();
    }//GEN-LAST:event_ButtonNilaiEditActionPerformed

    private void ButtonSimulasiAkhirEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirEditActionPerformed
        UpdateButtonCallback(_simulasiNilaiTableHandler, _simulasiNilaiTextFieldHandler, PanelAddSimulasiAkhir, "Simulasi Nilai");
                
        int rowIndex = _simulasiNilaiTableHandler.GetSelectedRowIndex();
        if(rowIndex == -1)
            return;
        Object valueM = _simulasiNilaiTableHandler.GetValueAt(SimulasiNilai.NamaMk, rowIndex);
        Object kodeMk = _mataKuliahTableHandler.GetValueAt(MataKuliah.No, _mataKuliahTableHandler.GetRowIndex(MataKuliah.Nama, valueM));
        FormSimulasiAkhirNamaMataKuliah.setSelectedItem(valueM);
        FormSimulasiAkhirKodeMataKuliah.setText(kodeMk.toString());
        FormSimulasiAkhirNamaMataKuliah.setEnabled(false);
        
        _simulasiNilaiFormState = FormState.Update;
        LabelTitleAddSimulasiAkhir.setText("Ubah Simulasi Nilai");
        ButtonSimulasiAkhir.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiAkhirEditActionPerformed

    private void ButtonMataKuliahCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMataKuliahCancelActionPerformed
        CancelConfirmation(_mataKuliahTextFieldHandler, PanelMataKuliah);
        ButtonDataMatkul.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMataKuliahCancelActionPerformed

    private void ButtonNilaiCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiCancelActionPerformed
        CancelConfirmation(_nilaiMahasiswaTextFieldHandler, PanelDataNilai);
        ButtonDataNilai.requestFocusInWindow();
    }//GEN-LAST:event_ButtonNilaiCancelActionPerformed

    private void ButtonSimulasiAkhirCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirCancelActionPerformed
        CancelConfirmation(_simulasiNilaiTextFieldHandler, PanelSimulasiAkhir);
        ButtonSimulasiAkhir.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiAkhirCancelActionPerformed

    private void ButtonDataMahasiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataMahasiswa1ActionPerformed
        LogOutConfirmation(PanelHomepage);
    }//GEN-LAST:event_ButtonDataMahasiswa1ActionPerformed

    private void ButtonMahasiswaSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaSimpanActionPerformed
        if(_mahasiswaFormState == FormState.Update)
            UpdateFormButtonCallback(_mahasiswaController.Update(), _mahasiswaTableHandler, _mahasiswaTextFieldHandler, PanelMahasiswa);
        else
            CreateFormButtonCallback(_mahasiswaController.Create(), _mahasiswaTableHandler, _mahasiswaTextFieldHandler, PanelMahasiswa);
    }//GEN-LAST:event_ButtonMahasiswaSimpanActionPerformed
    
    private void FormChooserTanggalLahirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FormChooserTanggalLahirPropertyChange
        if(evt.getPropertyName().equals("date"))
        {
            Date date = FormChooserTanggalLahir.getDate();
            FromMahasiswaTanggalLahir.setText(DateHelper.GetFormatedDate(date));
        }
    }//GEN-LAST:event_FormChooserTanggalLahirPropertyChange

    private void ButtonMahasiswaHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaHapusActionPerformed
        DeleteButtonCallback(_mahasiswaController.Delete(), _mahasiswaTableHandler);
    }//GEN-LAST:event_ButtonMahasiswaHapusActionPerformed

    private void ButtonMatkulHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMatkulHapusActionPerformed
        DeleteButtonCallback(_mataKuliahController.Delete(), _mataKuliahTableHandler);
    }//GEN-LAST:event_ButtonMatkulHapusActionPerformed

    private void ButtonMataKuliahSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMataKuliahSimpanActionPerformed
        if(_mataKuliahFormState == FormState.Update)
            UpdateFormButtonCallback(_mataKuliahController.Update(), _mataKuliahTableHandler, _mataKuliahTextFieldHandler, PanelMataKuliah);
        else
            CreateFormButtonCallback(_mataKuliahController.Create(), _mataKuliahTableHandler, _mataKuliahTextFieldHandler, PanelMataKuliah);
    }//GEN-LAST:event_ButtonMataKuliahSimpanActionPerformed

    private void FromMahasiswaNIMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FromMahasiswaNIMKeyTyped
        _mahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, Mahasiswa.Nim, 8);
    }//GEN-LAST:event_FromMahasiswaNIMKeyTyped

    private void FormNilaiKehadiranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNilaiKehadiranKeyTyped
        _nilaiMahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, NilaiMahasiswa.Kehadiran, 2);
    }//GEN-LAST:event_FormNilaiKehadiranKeyTyped

    private void FormNilaiTugas1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNilaiTugas1KeyTyped
        _nilaiMahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, NilaiMahasiswa.TugasPertama, 3);
    }//GEN-LAST:event_FormNilaiTugas1KeyTyped

    private void FormNilaiTugas2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNilaiTugas2KeyTyped
        _nilaiMahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, NilaiMahasiswa.TugasKedua, 3);
    }//GEN-LAST:event_FormNilaiTugas2KeyTyped

    private void FormNilaiTugas3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNilaiTugas3KeyTyped
        _nilaiMahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, NilaiMahasiswa.TugasKetiga, 3);
    }//GEN-LAST:event_FormNilaiTugas3KeyTyped

    private void FormNilaiUTSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNilaiUTSKeyTyped
        _nilaiMahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, NilaiMahasiswa.Uts, 3);
    }//GEN-LAST:event_FormNilaiUTSKeyTyped

    private void FormNilaiUASKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormNilaiUASKeyTyped
        _nilaiMahasiswaTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, NilaiMahasiswa.Uas, 3);
    }//GEN-LAST:event_FormNilaiUASKeyTyped

    private void FormSimulasiAkhirPresentaseAbsenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirPresentaseAbsenKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.PresentaseKehadiran, 3);
    }//GEN-LAST:event_FormSimulasiAkhirPresentaseAbsenKeyTyped

    private void FormSimulasiAkhirPresentaseUTSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirPresentaseUTSKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.PresentaseUts, 3);
    }//GEN-LAST:event_FormSimulasiAkhirPresentaseUTSKeyTyped

    private void FormSimulasiAkhirPresentaseUASKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirPresentaseUASKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.PresentaseUas, 3);
    }//GEN-LAST:event_FormSimulasiAkhirPresentaseUASKeyTyped

    private void FormSimulasiAkhirTugas1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirTugas1KeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.TugasPertama, 3);
    }//GEN-LAST:event_FormSimulasiAkhirTugas1KeyTyped

    private void FormSimulasiAkhirTugas2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirTugas2KeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.TugasKedua, 3);
    }//GEN-LAST:event_FormSimulasiAkhirTugas2KeyTyped

    private void FormSimulasiAkhirTugas3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirTugas3KeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.TugasKetiga, 3);
    }//GEN-LAST:event_FormSimulasiAkhirTugas3KeyTyped

    private void FormSimulasiAkhirUTSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirUTSKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.Uts, 3);
    }//GEN-LAST:event_FormSimulasiAkhirUTSKeyTyped

    private void FormSimulasiAkhirUASKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirUASKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.Uas, 3);
    }//GEN-LAST:event_FormSimulasiAkhirUASKeyTyped

    private void ButtonNilaiSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiSimpanActionPerformed
        if(_nilaiMahasiswaFormState == FormState.Update)
            UpdateFormButtonCallback(_nilaiMahasiswaController.Update(), _nilaiMahasiswaTableHandler, _nilaiMahasiswaTextFieldHandler, PanelDataNilai);
        else
            CreateFormButtonCallback(_nilaiMahasiswaController.Create(), _nilaiMahasiswaTableHandler, _nilaiMahasiswaTextFieldHandler, PanelDataNilai);
    }//GEN-LAST:event_ButtonNilaiSimpanActionPerformed

    private void FormSearchMahasiswaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSearchMahasiswaKeyTyped
        
    }//GEN-LAST:event_FormSearchMahasiswaKeyTyped

    private void ButtonNilaiHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiHapusActionPerformed
        DeleteButtonCallback(_nilaiMahasiswaController.Delete(), _nilaiMahasiswaTableHandler);
    }//GEN-LAST:event_ButtonNilaiHapusActionPerformed

    private void FormNilaiTugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormNilaiTugas2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FormNilaiTugas2ActionPerformed

    private void ButtonSearchNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSearchNilaiActionPerformed
        Search(_nilaiMahasiswaTableHandler, SearchNilai.getText(), NilaiMahasiswa.NamaMhs, NilaiMahasiswa.NamaMk);
    }//GEN-LAST:event_ButtonSearchNilaiActionPerformed

    private void ButtonSimulasiAkhirHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirHapusActionPerformed
        DeleteButtonCallback(_simulasiNilaiController.Delete(), _simulasiNilaiTableHandler);
    }//GEN-LAST:event_ButtonSimulasiAkhirHapusActionPerformed

    private void FormSimulasiAkhirPresentaseTugasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirPresentaseTugasKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.PresentaseTugas, 3);
    }//GEN-LAST:event_FormSimulasiAkhirPresentaseTugasKeyTyped

    private void FormSimulasiAkhirKehadiranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormSimulasiAkhirKehadiranKeyTyped
        _simulasiNilaiTextFieldHandler.PreventFieldHaveNonNumberCharacter(evt, SimulasiNilai.Kehadiran, 2);
    }//GEN-LAST:event_FormSimulasiAkhirKehadiranKeyTyped

    private void ButtonSimulasiKasusHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiKasusHapusActionPerformed
        DeleteButtonCallback(_transaksiController.Delete(), _transaksiTableHandler);      
    }//GEN-LAST:event_ButtonSimulasiKasusHapusActionPerformed

    private void ButtonSimulasiKasusEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiKasusEditActionPerformed
        // TODO add your handling code here:
        UpdateButtonCallback(_transaksiTableHandler, _transaksiTextFieldHandler, PanelAddSimulasiKasus, "Transaksi");
        
        FormSimulasiKasusOperator.setEnabled(false);
        FormSimulasiKasusNominal.setEnabled(false);
        FormSimulasiKasusMetodePembayaran.setEnabled(false);
        FormSimulasiKasusNoTelp.setEditable(false);

        int currentRowIndex = _transaksiTableHandler.GetSelectedRowIndex();
        if(currentRowIndex == -1)
            return;
        FormSimulasiKasusOperator.setSelectedItem(_transaksiTableHandler.GetValueAt(Transaksi.Operator, currentRowIndex));
        FormSimulasiKasusNominal.setSelectedItem(_transaksiTableHandler.GetValueAt(Transaksi.Nominal, currentRowIndex));
        FormSimulasiKasusMetodePembayaran.setSelectedItem(_transaksiTableHandler.GetValueAt(Transaksi.MetodePembayaran, currentRowIndex));
        FormSimulasiKasusStatus.setSelectedItem(_transaksiTableHandler.GetValueAt(Transaksi.Status, currentRowIndex));
               
        _transaksiFormState = FormState.Update;
        NavigateTo(PanelAddSimulasiKasus);
        LabelTitleAddSimulasiKasus.setText("Edit Simulasi Kasus");
        ButtonSimulasiKasus.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiKasusEditActionPerformed

    private void ButtonSimulasiKasusTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiKasusTambahActionPerformed
        // TODO add your handling code here:
        NavigateTo(PanelAddSimulasiKasus);
        FormSimulasiKasusOperator.setEnabled(true);
        FormSimulasiKasusNominal.setEnabled(true);
        FormSimulasiKasusMetodePembayaran.setEnabled(true);
        FormSimulasiKasusNoTelp.setEditable(true);
        
        _transaksiTextFieldHandler.ResetAllText();
        _transaksiFormState = FormState.Add;
        LabelTitleAddSimulasiKasus.setText("Tambah Simulasi Kasus");
        ButtonSimulasiKasus.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiKasusTambahActionPerformed

    private void ButtonCancelSimulasiKasusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelSimulasiKasusActionPerformed
        // TODO add your handling code here:
        CancelConfirmation(_simulasiNilaiTextFieldHandler, PanelSimulasiKasus);
        ButtonSimulasiKasus.requestFocusInWindow();
    }//GEN-LAST:event_ButtonCancelSimulasiKasusActionPerformed

    private void ButtonSimpanSimulasiKasusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimpanSimulasiKasusActionPerformed
        if(_transaksiFormState == FormState.Update)
            UpdateFormButtonCallback(_transaksiController.Update(), _transaksiTableHandler, _transaksiTextFieldHandler, PanelSimulasiKasus);
        else
            CreateFormButtonCallback(_transaksiController.Create(), _transaksiTableHandler, _transaksiTextFieldHandler, PanelSimulasiKasus);
    
    }//GEN-LAST:event_ButtonSimpanSimulasiKasusActionPerformed

    private <T extends Enum> void Search(JTableHandler handler, String text, T... columnKey)
    {
        handler.FilterTable(text, columnKey);
    }
    
    private void FormNilaiNamaMataKuliahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormNilaiNamaMataKuliahActionPerformed
       
    }//GEN-LAST:event_FormNilaiNamaMataKuliahActionPerformed

    private void ButtonMahasiswaCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaCariActionPerformed
        Search(_mahasiswaTableHandler, FormSearchMahasiswa.getText(), Mahasiswa.Nim, Mahasiswa.Nama);
    }//GEN-LAST:event_ButtonMahasiswaCariActionPerformed

    private void ButtonSimulasiKasusCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiKasusCariActionPerformed
        Search(_transaksiTableHandler, FieldSimulasiKasusPencarian.getText(), Transaksi.id, Transaksi.NomorTelp);
    }//GEN-LAST:event_ButtonSimulasiKasusCariActionPerformed

    private void ButtonSearchMatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSearchMatkulActionPerformed
        Search(_mataKuliahTableHandler, SearchMatkul.getText(), MataKuliah.No, MataKuliah.Nama);
    }//GEN-LAST:event_ButtonSearchMatkulActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelSimulasiKasus;
    private javax.swing.JButton ButtonDataMahasiswa;
    private javax.swing.JButton ButtonDataMahasiswa1;
    private javax.swing.JButton ButtonDataMatkul;
    private javax.swing.JButton ButtonDataNilai;
    private javax.swing.JButton ButtonHomePage;
    private javax.swing.JButton ButtonMahasiswaCancel;
    private javax.swing.JButton ButtonMahasiswaCari;
    private javax.swing.JButton ButtonMahasiswaEdit;
    private javax.swing.JButton ButtonMahasiswaHapus;
    private javax.swing.JButton ButtonMahasiswaSimpan;
    private javax.swing.JButton ButtonMahasiswaTambah;
    private javax.swing.JButton ButtonMataKuliahCancel;
    private javax.swing.JButton ButtonMataKuliahSimpan;
    private javax.swing.JButton ButtonMatkulEdit;
    private javax.swing.JButton ButtonMatkulHapus;
    private javax.swing.JButton ButtonMatkulTambah;
    private javax.swing.JButton ButtonNilaiCancel;
    private javax.swing.JButton ButtonNilaiEdit;
    private javax.swing.JButton ButtonNilaiHapus;
    private javax.swing.JButton ButtonNilaiSimpan;
    private javax.swing.JButton ButtonNilaiTambah;
    private javax.swing.JButton ButtonSearchMatkul;
    private javax.swing.JButton ButtonSearchNilai;
    private javax.swing.JButton ButtonSimpanSimulasiKasus;
    private javax.swing.JButton ButtonSimulasiAkhir;
    private javax.swing.JButton ButtonSimulasiAkhirCancel;
    private javax.swing.JButton ButtonSimulasiAkhirEdit;
    private javax.swing.JButton ButtonSimulasiAkhirHapus;
    private javax.swing.JButton ButtonSimulasiAkhirSimpan;
    private javax.swing.JButton ButtonSimulasiAkhirTambah;
    private javax.swing.JButton ButtonSimulasiKasus;
    private javax.swing.JButton ButtonSimulasiKasusCari;
    private javax.swing.JButton ButtonSimulasiKasusEdit;
    private javax.swing.JButton ButtonSimulasiKasusHapus;
    private javax.swing.JButton ButtonSimulasiKasusTambah;
    private javax.swing.JTextField FieldSimulasiKasusPencarian;
    private com.toedter.calendar.JDateChooser FormChooserTanggalLahir;
    private javax.swing.JFormattedTextField FormNilaiAngkatan;
    private javax.swing.JTextField FormNilaiKehadiran;
    private javax.swing.JTextField FormNilaiKodeMataKuliah;
    private javax.swing.JComboBox<String> FormNilaiNama;
    private javax.swing.JComboBox<String> FormNilaiNamaMataKuliah;
    private javax.swing.JTextField FormNilaiNim;
    private javax.swing.JTextField FormNilaiTugas1;
    private javax.swing.JTextField FormNilaiTugas2;
    private javax.swing.JTextField FormNilaiTugas3;
    private javax.swing.JTextField FormNilaiUAS;
    private javax.swing.JTextField FormNilaiUTS;
    private javax.swing.JTextField FormSearchMahasiswa;
    private javax.swing.JTextField FormSimulasiAkhirKehadiran;
    private javax.swing.JTextField FormSimulasiAkhirKodeMataKuliah;
    private javax.swing.JComboBox<String> FormSimulasiAkhirNamaMataKuliah;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseAbsen;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseTugas;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseUAS;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseUTS;
    private javax.swing.JTextField FormSimulasiAkhirTugas1;
    private javax.swing.JTextField FormSimulasiAkhirTugas2;
    private javax.swing.JTextField FormSimulasiAkhirTugas3;
    private javax.swing.JTextField FormSimulasiAkhirUAS;
    private javax.swing.JTextField FormSimulasiAkhirUTS;
    private javax.swing.JComboBox<String> FormSimulasiKasusMetodePembayaran;
    private javax.swing.JTextField FormSimulasiKasusNoTelp;
    private javax.swing.JComboBox<String> FormSimulasiKasusNominal;
    private javax.swing.JComboBox<String> FormSimulasiKasusOperator;
    private javax.swing.JComboBox<String> FormSimulasiKasusStatus;
    private javax.swing.JTextArea FromMahasiswaAlamat;
    private javax.swing.JTextField FromMahasiswaNIM;
    private javax.swing.JTextField FromMahasiswaNama;
    private javax.swing.JFormattedTextField FromMahasiswaTanggalLahir;
    private javax.swing.JTextField FromMahasiswaTempatLahir;
    private javax.swing.JTextField FromMataKuliahNama;
    private javax.swing.JTextField FromMataKuliahNomor;
    private javax.swing.JLabel LabelFromMataKuliahNama;
    private javax.swing.JLabel LabelFromMataKuliahNomor;
    private javax.swing.JLabel LabelMahasiswaAlamat;
    private javax.swing.JLabel LabelMahasiswaNIM;
    private javax.swing.JLabel LabelMahasiswaNama;
    private javax.swing.JLabel LabelMahasiswaTanggalLahir;
    private javax.swing.JLabel LabelMahasiswaTempatLahir;
    private javax.swing.JLabel LabelNilaiAngkatan;
    private javax.swing.JLabel LabelNilaiKehadiran;
    private javax.swing.JLabel LabelNilaiKodeMataKuliah;
    private javax.swing.JLabel LabelNilaiNIM;
    private javax.swing.JLabel LabelNilaiNama;
    private javax.swing.JLabel LabelNilaiNamaMataKuliah;
    private javax.swing.JLabel LabelNilaiPertemuan;
    private javax.swing.JLabel LabelNilaiTugas1;
    private javax.swing.JLabel LabelNilaiTugas2;
    private javax.swing.JLabel LabelNilaiTugas3;
    private javax.swing.JLabel LabelNilaiUAS;
    private javax.swing.JLabel LabelNilaiUTS;
    private javax.swing.JLabel LabelSimulasiAkhirKehadiran;
    private javax.swing.JLabel LabelSimulasiAkhirKodeMataKuliah;
    private javax.swing.JLabel LabelSimulasiAkhirNamaMataKuliah;
    private javax.swing.JLabel LabelSimulasiAkhirPertemuan;
    private javax.swing.JLabel LabelSimulasiAkhirPresentaseAbsen;
    private javax.swing.JLabel LabelSimulasiAkhirPresentaseAbsen1;
    private javax.swing.JLabel LabelSimulasiAkhirPresentaseUAS;
    private javax.swing.JLabel LabelSimulasiAkhirPresentaseUTS;
    private javax.swing.JLabel LabelSimulasiAkhirTugas1;
    private javax.swing.JLabel LabelSimulasiAkhirTugas2;
    private javax.swing.JLabel LabelSimulasiAkhirTugas3;
    private javax.swing.JLabel LabelSimulasiAkhirUAS;
    private javax.swing.JLabel LabelSimulasiAkhirUTS;
    private javax.swing.JLabel LabelSimulasiKasusMetodePembayaran;
    private javax.swing.JLabel LabelSimulasiKasusNoTelp;
    private javax.swing.JLabel LabelSimulasiKasusNominal;
    private javax.swing.JLabel LabelSimulasiKasusOperator;
    private javax.swing.JLabel LabelSimulasiKasusStatus;
    private javax.swing.JLabel LabelTitleAddMahasiswa;
    private javax.swing.JLabel LabelTitleAddMataKuliah;
    private javax.swing.JLabel LabelTitleAddNilai;
    private javax.swing.JLabel LabelTitleAddSimulasiAkhir;
    private javax.swing.JLabel LabelTitleAddSimulasiKasus;
    private javax.swing.JLabel LabelTitleHome;
    private javax.swing.JLabel LabelTitleMahasiswa;
    private javax.swing.JLabel LabelTitleMataKuliah;
    private javax.swing.JLabel LabelTitleNilai;
    private javax.swing.JLabel LabelTitleSimulasiAkhir;
    private javax.swing.JLabel LabelTitleSimulasiKasus;
    private javax.swing.JPanel LayoutNilai;
    private javax.swing.JPanel LayoutPresensi;
    private javax.swing.JPanel LayoutSimulasiKasusKanan;
    private javax.swing.JPanel LayoutSimulasiKasusKiri;
    private javax.swing.JPanel MahasiswaLayoutKiri;
    private javax.swing.JPanel MahasiswaLayoutKiri1;
    private javax.swing.JPanel PanelAddMahasiswa;
    private javax.swing.JPanel PanelAddMataKuliah;
    private javax.swing.JPanel PanelAddNilai;
    private javax.swing.JPanel PanelAddSimulasiAkhir;
    private javax.swing.JPanel PanelAddSimulasiKasus;
    private javax.swing.JPanel PanelContent;
    private javax.swing.JPanel PanelDataNilai;
    private javax.swing.JPanel PanelHomepage;
    private javax.swing.JPanel PanelMahasiswa;
    private javax.swing.JPanel PanelMataKuliah;
    private javax.swing.JPanel PanelMenu;
    private javax.swing.JPanel PanelSimulasiAkhir;
    private javax.swing.JPanel PanelSimulasiKasus;
    private javax.swing.JTextField SearchMatkul;
    private javax.swing.JTextField SearchNilai;
    private javax.swing.JTable TableMahasiswa;
    private javax.swing.JTable TableMatkul;
    private javax.swing.JTable TableNilai;
    private javax.swing.JTable TableSimulasiAkhir;
    private javax.swing.JTable TableSimulasiKasus;
    private javax.swing.JPanel infoMataKuliah;
    private javax.swing.JPanel informasiMahasiwa;
    private javax.swing.JPanel informasi_mata_kuliah;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JCalendar jCalendar2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel logo;
    // End of variables declaration//GEN-END:variables
}
