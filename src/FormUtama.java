import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.LinkedHashMap;
import edu.kemahasiswaan.table.*;
import edu.kemahasiswaan.handler.*;
import edu.kemahasiswaan.controller.*;
import edu.kemahasiswaan.validation.*;
import javax.swing.text.JTextComponent;
import edu.kemahasiswaan.helper.DateFormatHelper;
import edu.kemahasiswaan.response.Response;

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
    private MahasiswaValidation _mahasiswaValidation;
    private MataKuliahValidation _mataKuliahValidation;

    private MahasiswaController _mahasiswaController;
    private MataKuliahController _mataKuliahController;

    private JTableHandler<Mahasiswa> _mahasiswaTableHandler;
    private JTableHandler<MataKuliah> _mataKuliahTableHandler;
    
    private JTextFieldHandler<Mahasiswa> _mahasiswaTextFieldHandler;
    private JTextFieldHandler<Mahasiswa> _mataKuliahTextFieldHandler;

    public FormUtama() 
    {
        initComponents();
        InitializeHandler();
        InitFormValidation();
        InitializeController();
        LoadTable();
    }
    
    // <editor-fold desc="Initialize">
    private void InitializeHandler()
    {
        InitializeTableHandler();
        InitializeTextFieldHandler();
    }

    private void InitializeTableHandler()
    {
        _mataKuliahTableHandler = new JTableHandler<>(TableMatkul, MataKuliah.No);
        _mahasiswaTableHandler = new JTableHandler<>(TableMahasiswa, Mahasiswa.Nim);
    }

    private void InitializeTextFieldHandler()
    {
        _mahasiswaTextFieldHandler = new JTextFieldHandler(new LinkedHashMap<Mahasiswa, JTextComponent>()
        {{
            put(Mahasiswa.Nim, FromMahasiswaNIM);
            put(Mahasiswa.Nama, FromMahasiswaNama);
            put(Mahasiswa.Alamat, FromMahasiswaAlamat);
            put(Mahasiswa.TempatLahir, FromMahasiswaTempatLahir);
            put(Mahasiswa.TanggalLahir, FromMahasiswaTanggalLahir);
        }});

        _mataKuliahTextFieldHandler = new JTextFieldHandler(new LinkedHashMap<MataKuliah, JTextComponent>()
        {{
            put(MataKuliah.No, FromMataKuliahNomor);
            put(MataKuliah.Nama, FromMataKuliahNama);
        }});
    }

    private void InitFormValidation()
    {
        _mahasiswaValidation = new MahasiswaValidation(_mahasiswaTableHandler, _mahasiswaTextFieldHandler);
        _mataKuliahValidation = new MataKuliahValidation(_mataKuliahTableHandler, _mataKuliahTextFieldHandler);
    }

    private void InitializeController()
    {
        _mahasiswaController = new MahasiswaController(_mahasiswaValidation);
        _mataKuliahController = new MataKuliahController(_mataKuliahValidation);
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
    
    private void UpdateButtonCallback(JTableHandler tableHandler, JTextFieldHandler textFieldHandler)
    {
        var selectedRow = tableHandler.GetSelectedRowIndex();
        var rowData = tableHandler.GetRow(selectedRow);
        textFieldHandler.SetAllText(rowData);
    }
    
    private void DeleteButtonCallback(Response response, JTableHandler tableHandler)
    {
        if(response == null) return;
        tableHandler.Delete((Map)response.GetResult().getFirst());
    }
    // </editor-fold>
    
    private void LoadTable()
    {
        var tableMahasiswaData = _mahasiswaController.SelectAll().GetResult();
        _mahasiswaTableHandler.Load(tableMahasiswaData);
        
        var tableMataKuliahData = _mataKuliahController.SelectAll().GetResult();
        _mataKuliahTableHandler.Load(tableMataKuliahData);
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
        if(CancelDialog == JOptionPane.YES_OPTION)
            NavigateTo(Destination);
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
        jLabel1 = new javax.swing.JLabel();
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
        ButtonSearchMahasiswa = new javax.swing.JButton();
        ButtonMahasiswaHapus = new javax.swing.JButton();
        ButtonMahasiswaEdit = new javax.swing.JButton();
        ButtonMahasiswaTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableMahasiswa = new javax.swing.JTable();
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
        PanelAddMahasiswa = new javax.swing.JPanel();
        LabelTitleAddMahasiswa = new javax.swing.JLabel();
        LabelMahasiswaNIM = new javax.swing.JLabel();
        FromMahasiswaNIM = new javax.swing.JTextField();
        FromMahasiswaNama = new javax.swing.JTextField();
        LabelMahasiswaNama = new javax.swing.JLabel();
        LabelMahasiswaTempatLahir = new javax.swing.JLabel();
        FromMahasiswaTempatLahir = new javax.swing.JTextField();
        LabelMahasiswaTanggalLahir = new javax.swing.JLabel();
        FromMahasiswaTanggalLahir = new javax.swing.JFormattedTextField();
        LabelMahasiswaAlamat = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        FromMahasiswaAlamat = new javax.swing.JTextArea();
        ButtonMahasiswaSimpan = new javax.swing.JButton();
        ButtonMahasiswaCancel = new javax.swing.JButton();
        FormChooserTanggalLahir = new com.toedter.calendar.JDateChooser();
        PanelAddMataKuliah = new javax.swing.JPanel();
        LabelTitleAddMataKuliah = new javax.swing.JLabel();
        LabelFromMataKuliahNomor = new javax.swing.JLabel();
        FromMataKuliahNomor = new javax.swing.JTextField();
        LabelFromMataKuliahNama = new javax.swing.JLabel();
        FromMataKuliahNama = new javax.swing.JTextField();
        ButtonMataKuliahCancel = new javax.swing.JButton();
        ButtonMataKuliahSimpan = new javax.swing.JButton();
        PanelAddNilai = new javax.swing.JPanel();
        LabelTitleAddNilai = new javax.swing.JLabel();
        LabelNilaiNIM = new javax.swing.JLabel();
        FormNilaiNama = new javax.swing.JTextField();
        FormNilaiKehadiran = new javax.swing.JTextField();
        FormNilaiTugas1 = new javax.swing.JTextField();
        FormNilaiTugas2 = new javax.swing.JTextField();
        FormNilaiTugas3 = new javax.swing.JTextField();
        LabelNilaiNama = new javax.swing.JLabel();
        LabelNilaiKehadiran = new javax.swing.JLabel();
        LabelNilaiTugas1 = new javax.swing.JLabel();
        LabelNilaiTugas2 = new javax.swing.JLabel();
        LabelNilaiTugas3 = new javax.swing.JLabel();
        LabelNilaiNamaMataKuliah = new javax.swing.JLabel();
        FormNilaiKodeMataKuliah = new javax.swing.JTextField();
        LabelNilaiKodeMataKuliah = new javax.swing.JLabel();
        LabelNilaiUTS = new javax.swing.JLabel();
        FormNilaiUTS = new javax.swing.JTextField();
        LabelNilaiUAS = new javax.swing.JLabel();
        FormNilaiUAS = new javax.swing.JTextField();
        LabelNilaiAngkatan = new javax.swing.JLabel();
        FormNilaiAngkatan = new javax.swing.JFormattedTextField();
        LabelNilaiPertemuan = new javax.swing.JLabel();
        ButtonNilaiCancel = new javax.swing.JButton();
        ButtonNilaiSimpan = new javax.swing.JButton();
        FormNilaiNim = new javax.swing.JComboBox<>();
        FormNilaiNamaMataKuliah = new javax.swing.JComboBox<>();
        PanelAddSimulasiAkhir = new javax.swing.JPanel();
        LabelTitleAddSimulasiAkhir = new javax.swing.JLabel();
        LabelSimulasiAkhirKehadiran = new javax.swing.JLabel();
        FormSimulasiAkhirKehadiran = new javax.swing.JTextField();
        LabelSimulasiAkhirPertemuan = new javax.swing.JLabel();
        LabelSimulasiAkhirTugas1 = new javax.swing.JLabel();
        FormSimulasiAkhirTugas1 = new javax.swing.JTextField();
        LabelSimulasiAkhirTugas2 = new javax.swing.JLabel();
        FormSimulasiAkhirTugas2 = new javax.swing.JTextField();
        LabelSimulasiAkhirTugas3 = new javax.swing.JLabel();
        FormSimulasiAkhirTugas3 = new javax.swing.JTextField();
        LabelSimulasiAkhirUTS = new javax.swing.JLabel();
        FormSimulasiAkhirUTS = new javax.swing.JTextField();
        LabelSimulasiAkhirUAS = new javax.swing.JLabel();
        FormSimulasiAkhirUAS = new javax.swing.JTextField();
        LabelSimulasiAkhirNamaMataKuliah = new javax.swing.JLabel();
        FormSimulasiAkhirNamaMataKuliah = new javax.swing.JTextField();
        LabelSimulasiAkhirKodeMataKuliah = new javax.swing.JLabel();
        FormSimulasiAkhirKodeMataKuliah = new javax.swing.JTextField();
        LabelSimulasiAkhirPresentaseAbsen = new javax.swing.JLabel();
        FormSimulasiAkhirPresentaseAbsen = new javax.swing.JTextField();
        LabelSimulasiAkhirPresentaseUTS = new javax.swing.JLabel();
        FormSimulasiAkhirPresentaseUTS = new javax.swing.JTextField();
        FormSimulasiAkhirPresentaseUAS = new javax.swing.JTextField();
        LabelSimulasiAkhirPresentaseUAS = new javax.swing.JLabel();
        ButtonSimulasiAkhirCancel = new javax.swing.JButton();
        ButtonSimulasiAkhirSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(244, 245, 246));
        setResizable(false);

        PanelMenu.setBackground(new java.awt.Color(255, 255, 255));

        ButtonHomePage.setText("Home Page");
        ButtonHomePage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonHomePageActionPerformed(evt);
            }
        });

        ButtonDataMahasiswa.setText("Data Mahasiswa");
        ButtonDataMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataMahasiswaActionPerformed(evt);
            }
        });

        ButtonDataMatkul.setText("Data Mata Kuliah");
        ButtonDataMatkul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataMatkulActionPerformed(evt);
            }
        });

        ButtonDataNilai.setText("Data Nilai");
        ButtonDataNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDataNilaiActionPerformed(evt);
            }
        });

        ButtonSimulasiAkhir.setText("Simulasi Akhir");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(16, 16, 16))
        );
        PanelMenuLayout.setVerticalGroup(
            PanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMenuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(74, 74, 74)
                .addComponent(ButtonDataMahasiswa1)
                .addGap(51, 51, 51))
        );

        PanelContent.setBackground(new java.awt.Color(244, 245, 246));
        PanelContent.setToolTipText("");
        PanelContent.setLayout(new java.awt.CardLayout());

        PanelHomepage.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleHome.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleHome.setText("Home Page");

        jLabel3.setText("Photo");

        jLabel4.setText("10119907");

        jLabel5.setText("T Doni Indrapasta");

        jLabel6.setText("Photo");

        jLabel7.setText("Aris Prabowo");

        jLabel8.setText("10119914");

        javax.swing.GroupLayout PanelHomepageLayout = new javax.swing.GroupLayout(PanelHomepage);
        PanelHomepage.setLayout(PanelHomepageLayout);
        PanelHomepageLayout.setHorizontalGroup(
            PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHomepageLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(265, 265, 265))
            .addGroup(PanelHomepageLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(LabelTitleHome)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelHomepageLayout.setVerticalGroup(
            PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHomepageLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(LabelTitleHome)
                .addGap(143, 143, 143)
                .addGroup(PanelHomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHomepageLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(PanelHomepageLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addContainerGap(280, Short.MAX_VALUE))
        );

        PanelContent.add(PanelHomepage, "card2");

        LabelTitleMahasiswa.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleMahasiswa.setText("Data Mahasiswa");

        ButtonSearchMahasiswa.setText("Cari");
        ButtonSearchMahasiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSearchMahasiswaActionPerformed(evt);
            }
        });

        ButtonMahasiswaHapus.setText("Hapus");
        ButtonMahasiswaHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMahasiswaHapusActionPerformed(evt);
            }
        });

        ButtonMahasiswaEdit.setText("Edit");
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

        javax.swing.GroupLayout PanelMahasiswaLayout = new javax.swing.GroupLayout(PanelMahasiswa);
        PanelMahasiswa.setLayout(PanelMahasiswaLayout);
        PanelMahasiswaLayout.setHorizontalGroup(
            PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMahasiswaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(PanelMahasiswaLayout.createSequentialGroup()
                            .addComponent(FormSearchMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonSearchMahasiswa)
                            .addGap(137, 137, 137)
                            .addComponent(ButtonMahasiswaHapus)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonMahasiswaEdit)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ButtonMahasiswaTambah))
                        .addComponent(jScrollPane1))
                    .addComponent(LabelTitleMahasiswa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelMahasiswaLayout.setVerticalGroup(
            PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMahasiswaLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(LabelTitleMahasiswa)
                .addGap(26, 26, 26)
                .addGroup(PanelMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FormSearchMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSearchMahasiswa)
                    .addComponent(ButtonMahasiswaHapus)
                    .addComponent(ButtonMahasiswaEdit)
                    .addComponent(ButtonMahasiswaTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        PanelContent.add(PanelMahasiswa, "card3");

        LabelTitleMataKuliah.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleMataKuliah.setText("Data Mata Kuliah");

        ButtonSearchMatkul.setText("Cari");

        ButtonMatkulHapus.setText("Hapus");
        ButtonMatkulHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMatkulHapusActionPerformed(evt);
            }
        });

        ButtonMatkulEdit.setText("Edit");
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
                .addGroup(PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMataKuliahLayout.createSequentialGroup()
                        .addComponent(SearchMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearchMatkul)
                        .addGap(137, 137, 137)
                        .addComponent(ButtonMatkulHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMatkulEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMatkulTambah))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTitleMataKuliah))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        PanelMataKuliahLayout.setVerticalGroup(
            PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMataKuliahLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(LabelTitleMataKuliah)
                .addGap(27, 27, 27)
                .addGroup(PanelMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSearchMatkul)
                    .addComponent(ButtonMatkulHapus)
                    .addComponent(ButtonMatkulEdit)
                    .addComponent(ButtonMatkulTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        PanelContent.add(PanelMataKuliah, "card4");

        LabelTitleNilai.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleNilai.setText("Data Nilai");

        ButtonSearchNilai.setText("Cari");

        ButtonNilaiHapus.setText("Hapus");

        ButtonNilaiEdit.setText("Edit");
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
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "Mata Kuliah", "Absensi", "Tgs1", "Tgs2", "Tgs3", "UTS", "UAS", "Nilai Absen", "Nilai Tugas", "Nilai UTS", "Nilai UAS", "Nilai Akhir", "Indeks", "Keterengan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
                    .addGroup(PanelDataNilaiLayout.createSequentialGroup()
                        .addComponent(SearchNilai, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSearchNilai)
                        .addGap(137, 137, 137)
                        .addComponent(ButtonNilaiHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonNilaiEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonNilaiTambah))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTitleNilai))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        PanelDataNilaiLayout.setVerticalGroup(
            PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDataNilaiLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(LabelTitleNilai)
                .addGap(30, 30, 30)
                .addGroup(PanelDataNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSearchNilai)
                    .addComponent(ButtonNilaiHapus)
                    .addComponent(ButtonNilaiEdit)
                    .addComponent(ButtonNilaiTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        PanelContent.add(PanelDataNilai, "card5");

        LabelTitleSimulasiAkhir.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleSimulasiAkhir.setText("Simulasi Akhir");

        TableSimulasiAkhir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nama MK", "Absen (%)", "Tugas (%)", "UTS (%)", "UAS (%)", "Absensi", "Tgs1", "Tgs2", "Tgs3", "UTS", "UAS", "Nilai Absen", "Nilai Tugas", "Niali UTS", "Nilai UAS", "Nilai Akhir", "Indeks", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TableSimulasiAkhir);

        ButtonSimulasiAkhirHapus.setText("Hapus");

        ButtonSimulasiAkhirEdit.setText("Edit");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSimulasiAkhirLayout.createSequentialGroup()
                .addGroup(PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelSimulasiAkhirLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelTitleSimulasiAkhir)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(PanelSimulasiAkhirLayout.createSequentialGroup()
                        .addContainerGap(480, Short.MAX_VALUE)
                        .addComponent(ButtonSimulasiAkhirHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiAkhirEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiAkhirTambah)))
                .addGap(33, 33, 33))
        );
        PanelSimulasiAkhirLayout.setVerticalGroup(
            PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiAkhirLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(LabelTitleSimulasiAkhir)
                .addGap(18, 18, 18)
                .addGroup(PanelSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSimulasiAkhirHapus)
                    .addComponent(ButtonSimulasiAkhirEdit)
                    .addComponent(ButtonSimulasiAkhirTambah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        PanelContent.add(PanelSimulasiAkhir, "card6");

        LabelTitleSimulasiKasus.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleSimulasiKasus.setText("Simulasi Kasus");

        javax.swing.GroupLayout PanelSimulasiKasusLayout = new javax.swing.GroupLayout(PanelSimulasiKasus);
        PanelSimulasiKasus.setLayout(PanelSimulasiKasusLayout);
        PanelSimulasiKasusLayout.setHorizontalGroup(
            PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiKasusLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(LabelTitleSimulasiKasus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelSimulasiKasusLayout.setVerticalGroup(
            PanelSimulasiKasusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSimulasiKasusLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(LabelTitleSimulasiKasus)
                .addContainerGap(508, Short.MAX_VALUE))
        );

        PanelContent.add(PanelSimulasiKasus, "card7");

        PanelAddMahasiswa.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddMahasiswa.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddMahasiswa.setText("Tambah Mahasiswa");

        LabelMahasiswaNIM.setText("NIM");

        LabelMahasiswaNama.setText("Nama");

        LabelMahasiswaTempatLahir.setText("Tempat Lahir");

        LabelMahasiswaTanggalLahir.setText("Tanggal Lahir");

        LabelMahasiswaAlamat.setText("Alamat");

        FromMahasiswaAlamat.setColumns(20);
        FromMahasiswaAlamat.setRows(5);
        jScrollPane5.setViewportView(FromMahasiswaAlamat);

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

        FormChooserTanggalLahir.setDateFormatString("MMMM d, y");
        FormChooserTanggalLahir.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FormChooserTanggalLahirPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout PanelAddMahasiswaLayout = new javax.swing.GroupLayout(PanelAddMahasiswa);
        PanelAddMahasiswa.setLayout(PanelAddMahasiswaLayout);
        PanelAddMahasiswaLayout.setHorizontalGroup(
            PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                        .addComponent(ButtonMahasiswaCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMahasiswaSimpan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                        .addComponent(LabelTitleAddMahasiswa)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAddMahasiswaLayout.createSequentialGroup()
                        .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FromMahasiswaTanggalLahir, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FromMahasiswaTempatLahir, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FromMahasiswaNama, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FromMahasiswaNIM, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelAddMahasiswaLayout.createSequentialGroup()
                                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelMahasiswaTanggalLahir, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelMahasiswaTempatLahir, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelMahasiswaNama, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelMahasiswaNIM, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                                .addComponent(LabelMahasiswaAlamat)
                                .addGap(202, 202, 202)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormChooserTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))))
        );
        PanelAddMahasiswaLayout.setVerticalGroup(
            PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(LabelTitleAddMahasiswa)
                .addGap(36, 36, 36)
                .addComponent(LabelMahasiswaNIM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMahasiswaNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelMahasiswaNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMahasiswaNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelMahasiswaTempatLahir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMahasiswaTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(LabelMahasiswaTanggalLahir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddMahasiswaLayout.createSequentialGroup()
                        .addComponent(FromMahasiswaTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelMahasiswaAlamat))
                    .addComponent(FormChooserTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(PanelAddMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonMahasiswaSimpan)
                    .addComponent(ButtonMahasiswaCancel))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddMahasiswa, "card8");

        PanelAddMataKuliah.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddMataKuliah.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddMataKuliah.setText("Tambah Mahasiswa");

        LabelFromMataKuliahNomor.setText("Nomor Mata Kuliah");

        LabelFromMataKuliahNama.setText("Nama Mata Kuliah");

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

        javax.swing.GroupLayout PanelAddMataKuliahLayout = new javax.swing.GroupLayout(PanelAddMataKuliah);
        PanelAddMataKuliah.setLayout(PanelAddMataKuliahLayout);
        PanelAddMataKuliahLayout.setHorizontalGroup(
            PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                        .addComponent(ButtonMataKuliahCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonMataKuliahSimpan))
                    .addGroup(PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(FromMataKuliahNama)
                        .addComponent(LabelFromMataKuliahNama)
                        .addComponent(LabelFromMataKuliahNomor)
                        .addComponent(LabelTitleAddMataKuliah)
                        .addComponent(FromMataKuliahNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        PanelAddMataKuliahLayout.setVerticalGroup(
            PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddMataKuliahLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(LabelTitleAddMataKuliah)
                .addGap(35, 35, 35)
                .addComponent(LabelFromMataKuliahNomor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMataKuliahNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelFromMataKuliahNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FromMataKuliahNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(PanelAddMataKuliahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonMataKuliahCancel)
                    .addComponent(ButtonMataKuliahSimpan))
                .addContainerGap(297, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddMataKuliah, "card9");

        PanelAddNilai.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddNilai.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddNilai.setText("Tambah Nilai");

        LabelNilaiNIM.setText("Nim");

        LabelNilaiNama.setText("Nama");

        LabelNilaiKehadiran.setText("Kehadiran");

        LabelNilaiTugas1.setText("Tugas 1");

        LabelNilaiTugas2.setText("Tugas 2");

        LabelNilaiTugas3.setText("Tugas 3");

        LabelNilaiNamaMataKuliah.setText("Nama Mata Kuliah");

        LabelNilaiKodeMataKuliah.setText("Kode Mata Kuliah");

        LabelNilaiUTS.setText("UTS");

        LabelNilaiUAS.setText("UAS");

        LabelNilaiAngkatan.setText("Angkatan");

        LabelNilaiPertemuan.setText("Pertemuan");

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

        javax.swing.GroupLayout PanelAddNilaiLayout = new javax.swing.GroupLayout(PanelAddNilai);
        PanelAddNilai.setLayout(PanelAddNilaiLayout);
        PanelAddNilaiLayout.setHorizontalGroup(
            PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FormNilaiNama)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(FormNilaiKehadiran, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelNilaiPertemuan)
                        .addGap(6, 6, 6))
                    .addComponent(FormNilaiTugas1)
                    .addComponent(FormNilaiTugas2)
                    .addComponent(FormNilaiTugas3)
                    .addComponent(FormNilaiNim, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(ButtonNilaiCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonNilaiSimpan))
                    .addComponent(LabelNilaiTugas3)
                    .addComponent(LabelNilaiTugas2)
                    .addComponent(LabelNilaiTugas1)
                    .addComponent(LabelNilaiKehadiran)
                    .addComponent(LabelNilaiNama)
                    .addComponent(LabelNilaiNIM)
                    .addComponent(LabelTitleAddNilai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelNilaiNamaMataKuliah)
                    .addComponent(LabelNilaiKodeMataKuliah)
                    .addComponent(FormNilaiKodeMataKuliah, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addComponent(LabelNilaiUTS)
                    .addComponent(FormNilaiUTS, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addComponent(LabelNilaiUAS)
                    .addComponent(FormNilaiUAS, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addComponent(LabelNilaiAngkatan)
                    .addComponent(FormNilaiAngkatan)
                    .addComponent(FormNilaiNamaMataKuliah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(71, 71, 71))
        );
        PanelAddNilaiLayout.setVerticalGroup(
            PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelTitleAddNilai)
                        .addGap(36, 36, 36)
                        .addComponent(LabelNilaiNamaMataKuliah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FormNilaiNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FormNilaiNamaMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiNIM)
                        .addGap(25, 25, 25)))
                .addGap(18, 18, 18)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiNama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiKodeMataKuliah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiKodeMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiKehadiran)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FormNilaiKehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelNilaiPertemuan)))
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiUTS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiTugas1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiTugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiUAS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiTugas2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiTugas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelNilaiTugas3))
                    .addGroup(PanelAddNilaiLayout.createSequentialGroup()
                        .addComponent(LabelNilaiAngkatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormNilaiAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormNilaiTugas3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelAddNilaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonNilaiCancel)
                    .addComponent(ButtonNilaiSimpan))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        PanelContent.add(PanelAddNilai, "card10");

        PanelAddSimulasiAkhir.setBackground(new java.awt.Color(244, 245, 246));

        LabelTitleAddSimulasiAkhir.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        LabelTitleAddSimulasiAkhir.setText("Tambah Simulasi Akhir");

        LabelSimulasiAkhirKehadiran.setText("Kehadiran");

        LabelSimulasiAkhirPertemuan.setText("Pertemuan");

        LabelSimulasiAkhirTugas1.setText("Tugas 1");

        LabelSimulasiAkhirTugas2.setText("Tugas 2");

        LabelSimulasiAkhirTugas3.setText("Tugas 3");

        LabelSimulasiAkhirUTS.setText("UTS");

        LabelSimulasiAkhirUAS.setText("UAS");

        LabelSimulasiAkhirNamaMataKuliah.setText("Nama Mata Kuliah");

        LabelSimulasiAkhirKodeMataKuliah.setText("Kode Mata Kuliah");

        LabelSimulasiAkhirPresentaseAbsen.setText("Presentase Absen");

        LabelSimulasiAkhirPresentaseUTS.setText("Presentase UTS");

        LabelSimulasiAkhirPresentaseUAS.setText("Presentase UAS");

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

        javax.swing.GroupLayout PanelAddSimulasiAkhirLayout = new javax.swing.GroupLayout(PanelAddSimulasiAkhir);
        PanelAddSimulasiAkhir.setLayout(PanelAddSimulasiAkhirLayout);
        PanelAddSimulasiAkhirLayout.setHorizontalGroup(
            PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(ButtonSimulasiAkhirCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonSimulasiAkhirSimpan)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelSimulasiAkhirKodeMataKuliah)
                            .addComponent(LabelSimulasiAkhirNamaMataKuliah)
                            .addComponent(LabelTitleAddSimulasiAkhir)
                            .addComponent(LabelSimulasiAkhirPresentaseAbsen)
                            .addComponent(FormSimulasiAkhirPresentaseAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelSimulasiAkhirPresentaseUTS)
                            .addComponent(FormSimulasiAkhirPresentaseUTS, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelSimulasiAkhirPresentaseUAS)
                            .addComponent(FormSimulasiAkhirPresentaseUAS, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(FormSimulasiAkhirNamaMataKuliah, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FormSimulasiAkhirKodeMataKuliah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(LabelSimulasiAkhirKehadiran)
                                    .addComponent(FormSimulasiAkhirKehadiran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(LabelSimulasiAkhirPertemuan))
                            .addComponent(LabelSimulasiAkhirTugas3)
                            .addComponent(LabelSimulasiAkhirTugas2)
                            .addComponent(LabelSimulasiAkhirTugas1)
                            .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(FormSimulasiAkhirTugas3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FormSimulasiAkhirTugas2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FormSimulasiAkhirTugas1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(LabelSimulasiAkhirUTS, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FormSimulasiAkhirUTS, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(LabelSimulasiAkhirUAS, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FormSimulasiAkhirUAS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)))
                        .addGap(61, 61, 61))))
        );
        PanelAddSimulasiAkhirLayout.setVerticalGroup(
            PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(LabelTitleAddSimulasiAkhir)
                .addGap(33, 33, 33)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(LabelSimulasiAkhirNamaMataKuliah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormSimulasiAkhirNamaMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(LabelSimulasiAkhirKehadiran)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FormSimulasiAkhirKehadiran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelSimulasiAkhirPertemuan))))
                .addGap(18, 18, 18)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
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
                        .addComponent(FormSimulasiAkhirUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelAddSimulasiAkhirLayout.createSequentialGroup()
                        .addComponent(LabelSimulasiAkhirKodeMataKuliah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormSimulasiAkhirKodeMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelSimulasiAkhirPresentaseAbsen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormSimulasiAkhirPresentaseAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelSimulasiAkhirPresentaseUTS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormSimulasiAkhirPresentaseUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(LabelSimulasiAkhirPresentaseUAS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FormSimulasiAkhirPresentaseUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(PanelAddSimulasiAkhirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonSimulasiAkhirCancel)
                    .addComponent(ButtonSimulasiAkhirSimpan))
                .addGap(35, 35, 35))
        );

        PanelContent.add(PanelAddSimulasiAkhir, "card11");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonDataMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataMahasiswaActionPerformed
        NavigateTo(PanelMahasiswa);
    }//GEN-LAST:event_ButtonDataMahasiswaActionPerformed

    private void ButtonDataMatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataMatkulActionPerformed
        NavigateTo(PanelMataKuliah);
    }//GEN-LAST:event_ButtonDataMatkulActionPerformed

    private void ButtonDataNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataNilaiActionPerformed
        NavigateTo(PanelDataNilai); 
    }//GEN-LAST:event_ButtonDataNilaiActionPerformed

    private void ButtonHomePageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHomePageActionPerformed
        NavigateTo(PanelHomepage);
    }//GEN-LAST:event_ButtonHomePageActionPerformed

    private void ButtonSimulasiAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirActionPerformed
        NavigateTo(PanelSimulasiAkhir);
    }//GEN-LAST:event_ButtonSimulasiAkhirActionPerformed

    private void ButtonSimulasiKasusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiKasusActionPerformed
        NavigateTo(PanelSimulasiKasus);
    }//GEN-LAST:event_ButtonSimulasiKasusActionPerformed

    private void ButtonMahasiswaCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaCancelActionPerformed
        CancelConfirmation(_mahasiswaTextFieldHandler, PanelMahasiswa);
        ButtonDataMahasiswa.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMahasiswaCancelActionPerformed

    private void ButtonNilaiSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiSimpanActionPerformed
    }//GEN-LAST:event_ButtonNilaiSimpanActionPerformed

    private void ButtonSimulasiAkhirSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirSimpanActionPerformed
        
    }//GEN-LAST:event_ButtonSimulasiAkhirSimpanActionPerformed

    private void ButtonMahasiswaTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaTambahActionPerformed
        NavigateTo(PanelAddMahasiswa);
        LabelTitleAddMahasiswa.setText("Tambah Mahasiswa");
        ButtonDataMahasiswa.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMahasiswaTambahActionPerformed

    private void ButtonMatkulTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMatkulTambahActionPerformed
        NavigateTo(PanelAddMataKuliah);
        LabelTitleAddMataKuliah.setText("Tambah Mata Kuliah");
        ButtonDataMatkul.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMatkulTambahActionPerformed

    private void ButtonNilaiTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiTambahActionPerformed
        NavigateTo(PanelAddNilai);
        LabelTitleAddNilai.setText("Tambah Nilai");
        ButtonDataNilai.requestFocusInWindow();
    }//GEN-LAST:event_ButtonNilaiTambahActionPerformed

    private void ButtonSimulasiAkhirTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirTambahActionPerformed
        NavigateTo(PanelAddSimulasiAkhir);
        LabelTitleAddSimulasiAkhir.setText("Tambah Simulasi Akhir");
        ButtonSimulasiAkhir.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiAkhirTambahActionPerformed

    private void ButtonMahasiswaEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaEditActionPerformed
        UpdateButtonCallback(_mahasiswaTableHandler, _mahasiswaTextFieldHandler);
        NavigateTo(PanelAddMahasiswa);      
        LabelTitleAddMahasiswa.setText("Edit Mahasiswa");
        ButtonDataMahasiswa.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMahasiswaEditActionPerformed

    private void ButtonMatkulEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMatkulEditActionPerformed
        UpdateButtonCallback(_mataKuliahTableHandler, _mataKuliahTextFieldHandler);
        NavigateTo(PanelAddMataKuliah);
        LabelTitleAddMataKuliah.setText("Edit Mata Kuliah");
        ButtonDataMatkul.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMatkulEditActionPerformed

    private void ButtonNilaiEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiEditActionPerformed
        NavigateTo(PanelAddNilai);
        LabelTitleAddNilai.setText("Edit Nilai");
        ButtonDataNilai.requestFocusInWindow();
    }//GEN-LAST:event_ButtonNilaiEditActionPerformed

    private void ButtonSimulasiAkhirEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirEditActionPerformed
        NavigateTo(PanelAddSimulasiAkhir);
        LabelTitleAddSimulasiAkhir.setText("Edit Simulasi Akhir");
        ButtonSimulasiAkhir.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiAkhirEditActionPerformed

    private void ButtonMataKuliahCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMataKuliahCancelActionPerformed
        CancelConfirmation(_mataKuliahTextFieldHandler, PanelMahasiswa);
        ButtonDataMatkul.requestFocusInWindow();
    }//GEN-LAST:event_ButtonMataKuliahCancelActionPerformed

    private void ButtonNilaiCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNilaiCancelActionPerformed
        CancelConfirmation(null, PanelDataNilai);
        ButtonDataNilai.requestFocusInWindow();
    }//GEN-LAST:event_ButtonNilaiCancelActionPerformed

    private void ButtonSimulasiAkhirCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSimulasiAkhirCancelActionPerformed
        CancelConfirmation(null, PanelSimulasiAkhir);
        ButtonSimulasiAkhir.requestFocusInWindow();
    }//GEN-LAST:event_ButtonSimulasiAkhirCancelActionPerformed

    private void ButtonDataMahasiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDataMahasiswa1ActionPerformed
        LogOutConfirmation(PanelHomepage);
    }//GEN-LAST:event_ButtonDataMahasiswa1ActionPerformed

    private void ButtonMahasiswaSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaSimpanActionPerformed
        //UpdateFormButtonCallback(_mahasiswaController.Update(), _mahasiswaTableHandler, _mahasiswaTextFieldHandler, PanelMahasiswa);
        CreateFormButtonCallback(_mahasiswaController.Create(), _mahasiswaTableHandler, _mahasiswaTextFieldHandler, PanelMahasiswa);
    }//GEN-LAST:event_ButtonMahasiswaSimpanActionPerformed
    
    private void FormChooserTanggalLahirPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FormChooserTanggalLahirPropertyChange
        if(evt.getPropertyName().equals("date"))
        {
            var date = FormChooserTanggalLahir.getDate();
            FromMahasiswaTanggalLahir.setText(DateFormatHelper.GetFormatedDate(date));
        }
    }//GEN-LAST:event_FormChooserTanggalLahirPropertyChange

    private void ButtonMahasiswaHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMahasiswaHapusActionPerformed
        DeleteButtonCallback(_mahasiswaController.Delete(), _mahasiswaTableHandler);
    }//GEN-LAST:event_ButtonMahasiswaHapusActionPerformed

    private void ButtonMatkulHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMatkulHapusActionPerformed
        DeleteButtonCallback(_mataKuliahController.Delete(), _mataKuliahTableHandler);
    }//GEN-LAST:event_ButtonMatkulHapusActionPerformed

    private void ButtonMataKuliahSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMataKuliahSimpanActionPerformed
        //UpdateFormButtonCallback(_mataKuliahController.Update(), _mataKuliahTableHandler, _mataKuliahTextFieldHandler, PanelMataKuliah);
        CreateFormButtonCallback(_mataKuliahController.Create(), _mataKuliahTableHandler, _mataKuliahTextFieldHandler, PanelMataKuliah);
    }//GEN-LAST:event_ButtonMataKuliahSimpanActionPerformed

    private void ButtonSearchMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSearchMahasiswaActionPerformed
        // TODO add your handling code here:
        _mahasiswaTableHandler.FilterTable(FormSearchMahasiswa.getText());
    }//GEN-LAST:event_ButtonSearchMahasiswaActionPerformed

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
    private javax.swing.JButton ButtonDataMahasiswa;
    private javax.swing.JButton ButtonDataMahasiswa1;
    private javax.swing.JButton ButtonDataMatkul;
    private javax.swing.JButton ButtonDataNilai;
    private javax.swing.JButton ButtonHomePage;
    private javax.swing.JButton ButtonMahasiswaCancel;
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
    private javax.swing.JButton ButtonSearchMahasiswa;
    private javax.swing.JButton ButtonSearchMatkul;
    private javax.swing.JButton ButtonSearchNilai;
    private javax.swing.JButton ButtonSimulasiAkhir;
    private javax.swing.JButton ButtonSimulasiAkhirCancel;
    private javax.swing.JButton ButtonSimulasiAkhirEdit;
    private javax.swing.JButton ButtonSimulasiAkhirHapus;
    private javax.swing.JButton ButtonSimulasiAkhirSimpan;
    private javax.swing.JButton ButtonSimulasiAkhirTambah;
    private javax.swing.JButton ButtonSimulasiKasus;
    private com.toedter.calendar.JDateChooser FormChooserTanggalLahir;
    private javax.swing.JFormattedTextField FormNilaiAngkatan;
    private javax.swing.JTextField FormNilaiKehadiran;
    private javax.swing.JTextField FormNilaiKodeMataKuliah;
    private javax.swing.JTextField FormNilaiNama;
    private javax.swing.JComboBox<String> FormNilaiNamaMataKuliah;
    private javax.swing.JComboBox<String> FormNilaiNim;
    private javax.swing.JTextField FormNilaiTugas1;
    private javax.swing.JTextField FormNilaiTugas2;
    private javax.swing.JTextField FormNilaiTugas3;
    private javax.swing.JTextField FormNilaiUAS;
    private javax.swing.JTextField FormNilaiUTS;
    private javax.swing.JTextField FormSearchMahasiswa;
    private javax.swing.JTextField FormSimulasiAkhirKehadiran;
    private javax.swing.JTextField FormSimulasiAkhirKodeMataKuliah;
    private javax.swing.JTextField FormSimulasiAkhirNamaMataKuliah;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseAbsen;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseUAS;
    private javax.swing.JTextField FormSimulasiAkhirPresentaseUTS;
    private javax.swing.JTextField FormSimulasiAkhirTugas1;
    private javax.swing.JTextField FormSimulasiAkhirTugas2;
    private javax.swing.JTextField FormSimulasiAkhirTugas3;
    private javax.swing.JTextField FormSimulasiAkhirUAS;
    private javax.swing.JTextField FormSimulasiAkhirUTS;
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
    private javax.swing.JLabel LabelSimulasiAkhirPresentaseUAS;
    private javax.swing.JLabel LabelSimulasiAkhirPresentaseUTS;
    private javax.swing.JLabel LabelSimulasiAkhirTugas1;
    private javax.swing.JLabel LabelSimulasiAkhirTugas2;
    private javax.swing.JLabel LabelSimulasiAkhirTugas3;
    private javax.swing.JLabel LabelSimulasiAkhirUAS;
    private javax.swing.JLabel LabelSimulasiAkhirUTS;
    private javax.swing.JLabel LabelTitleAddMahasiswa;
    private javax.swing.JLabel LabelTitleAddMataKuliah;
    private javax.swing.JLabel LabelTitleAddNilai;
    private javax.swing.JLabel LabelTitleAddSimulasiAkhir;
    private javax.swing.JLabel LabelTitleHome;
    private javax.swing.JLabel LabelTitleMahasiswa;
    private javax.swing.JLabel LabelTitleMataKuliah;
    private javax.swing.JLabel LabelTitleNilai;
    private javax.swing.JLabel LabelTitleSimulasiAkhir;
    private javax.swing.JLabel LabelTitleSimulasiKasus;
    private javax.swing.JPanel PanelAddMahasiswa;
    private javax.swing.JPanel PanelAddMataKuliah;
    private javax.swing.JPanel PanelAddNilai;
    private javax.swing.JPanel PanelAddSimulasiAkhir;
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
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JCalendar jCalendar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
