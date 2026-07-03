package view;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import model.domain.Funcionario;
import controller.ControladorCRUDFuncionarios;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import model.domain.TipoUsuario;
import utilitarios.Utilitarios;

public class CRUDFuncionariosFrame extends javax.swing.JInternalFrame {

    private Funcionario funcionarioLogado;
    private ControladorCRUDFuncionarios controlador;
    private DefaultTableModel tabela;
    private int idSelecionado;
    
    public CRUDFuncionariosFrame(Funcionario funcionario) {
        initComponents();
        labelEditandoFunc.setVisible(false);
        this.controlador = new ControladorCRUDFuncionarios();
        this.funcionarioLogado = funcionario;
        this.tabela = (DefaultTableModel) tabelaFuncionarios.getModel();
        tabelaFuncionarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tabelaFuncionarios.getSelectedRow();
                if (row >= 0) preencherFormulario(row);
            }   
        });
        preencherTabela(ControladorCRUDFuncionarios.search(null));
        preencherFormulario(funcionario);
    }

    private void preencherTabela(List<Funcionario> list) {
        tabela.setRowCount(0);
        list.forEach(f -> {
            Object[] dados = {  
                f.getIdFuncionario(),
                f.getNome(),
                f.getNomeUsuario(),
                f.getSexo(),
                f.getEmail(),
                formatarTelefone(f.getTelefone()),
                f.getDataNascimento(),
                formatarCpf(f.getCpf()),
                f.getTipoUsuario()
            };
            this.tabela.addRow(dados);
        });
    }
    
    private void preencherFormulario(int row) {    
        idSelecionado = (int) tabela.getValueAt(row, 0);
        labelEditandoFunc.setText("Editando Funcionário #" + idSelecionado);
        labelEditandoFunc.setVisible(true); 
        txtNome.setText((String) tabela.getValueAt(row, 1));
        txtNomeUsr.setText((String) tabela.getValueAt(row, 2));
        txtEmail.setText((String) tabela.getValueAt(row, 4));
        boxSexo.setSelectedItem(tabela.getValueAt(row, 3));
        TipoUsuario tipo = (TipoUsuario) tabela.getValueAt(row, 8);
        boxUsr.setSelectedItem(tipo.name());

        try {
            txtCpf.setValue((String) tabela.getValueAt(row, 7));
            LocalDate nasc = (LocalDate) tabela.getValueAt(row, 6);
            txtNasc.setValue(nasc.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            txtTel.setValue((String) tabela.getValueAt(row, 5));
        } catch (Exception ex) { }
    }
    
    private void preencherFormulario(Funcionario f) {
        idSelecionado = f.getIdFuncionario();
        labelEditandoFunc.setText("Editando Funcionário #" + idSelecionado);
        labelEditandoFunc.setVisible(true);
        txtNome.setText(f.getNome());
        txtNomeUsr.setText(f.getNomeUsuario());
        txtEmail.setText(f.getEmail());
        boxSexo.setSelectedItem(f.getSexo());
        boxUsr.setSelectedItem(f.getTipoUsuario().name());
        txtTel.setValue(formatarTelefone(f.getTelefone()));
        txtCpf.setValue(formatarCpf(f.getCpf()));
        
        try {
            String nascFormatado = f.getDataNascimento()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            txtNasc.setValue(nascFormatado);
        } catch (Exception ex) { }
    }
    
    public void limparFormulario() {
        txtNome.setText("");
        txtNomeUsr.setText("");
        txtEmail.setText("");
        txtCpf.setValue(null);
        txtNasc.setValue(null);
        txtTel.setValue(null);
        boxSexo.setSelectedIndex(0);
        boxUsr.setSelectedIndex(0);
        idSelecionado = -1;
        labelEditandoFunc.setVisible(false);
        tabelaFuncionarios.clearSelection();
        resetarTabela();
    }
    
    private Funcionario carregarFiltro() {
        Funcionario f = new Funcionario();
        f.setNome(txtNome.getText());
        f.setNomeUsuario(txtNomeUsr.getText());
        f.setEmail(txtEmail.getText());

        String sexo = (String) boxSexo.getSelectedItem();
        if (!sexo.equals("Selecione")) f.setSexo(sexo);

        f.setCpf(txtCpf.getText().replaceAll("[^0-9]", ""));
        f.setTelefone(txtTel.getText().replaceAll("[^0-9]", ""));

        String tipoStr = (String) boxUsr.getSelectedItem();
        if (!tipoStr.equals("Selecione")) f.setTipoUsuario(TipoUsuario.valueOf(tipoStr));

        return f;
    }
    
    private Funcionario carregarFuncionarioParaAtualizar() {
        Funcionario f = carregarFiltro();
        f.setIdFuncionario(idSelecionado); // passa o ID!

        try {
            String nascStr = txtNasc.getText().replaceAll("[^0-9/]", "");
            if (!nascStr.isBlank()) {
                f.setDataNascimento(LocalDate.parse(nascStr,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        } catch (Exception ex) {
            Utilitarios.warningMsg("Data de nascimento inválida", "Atenção");
        }
            return f;
    }
    
    private Funcionario carregarFuncionario() {
        Funcionario f = carregarFiltro();

        String nascStr = txtNasc.getText().replaceAll("[^0-9/]", "");
        if (nascStr.isBlank()) {
            Utilitarios.warningMsg("Preencha a data de nascimento.", "Atenção");
        }
        try {
            f.setDataNascimento(LocalDate.parse(nascStr,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception ex) {
            Utilitarios.warningMsg("Data de nascimento inválida", "Atenção");
            return null;
        }
        return f;
    }
    
    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.replaceAll("[^0-9]", "").length() != 11) return cpf;
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.substring(0,3)+"."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9);
    }

    private String formatarTelefone(String tel) {
        if (tel == null || tel.replaceAll("[^0-9]", "").length() != 11) return tel;
        tel = tel.replaceAll("[^0-9]", "");
        return "("+tel.substring(0,2)+") "+tel.substring(2,7)+"-"+tel.substring(7);
    }
    
    private void resetarTabela() {
        tabela.setRowCount(0);
        preencherTabela(ControladorCRUDFuncionarios.search(null));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaFuncionarios = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        txtNasc = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        boxSexo = new javax.swing.JComboBox<>();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNomeUsr = new javax.swing.JTextField();
        boxUsr = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        botaoSalvar = new javax.swing.JButton();
        botaoAtualizar = new javax.swing.JButton();
        botaoDeletar = new javax.swing.JButton();
        txtTel = new javax.swing.JFormattedTextField();
        botaoLimpar = new javax.swing.JButton();
        botaoFiltrar = new javax.swing.JButton();
        labelEditandoFunc = new javax.swing.JLabel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(744, 521));

        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Usuário", "Sexo", "Email", "Telefone", "Nascimento", "CPF", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaFuncionarios);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nome");

        txtNome.addActionListener(this::txtNomeActionPerformed);

        jLabel2.setText("CPF");

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpf.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        try {
            txtNasc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtNasc.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtNasc.setPreferredSize(new java.awt.Dimension(100, 22));

        jLabel3.setText("Data de nascimento");

        jLabel4.setText("Sexo");

        boxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Masculino", "Feminino" }));

        txtEmail.addActionListener(this::txtEmailActionPerformed);

        jLabel5.setText("Email");

        jLabel6.setText("Telefone");

        jLabel7.setText("Nome de usuário");

        txtNomeUsr.addActionListener(this::txtNomeUsrActionPerformed);

        boxUsr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "PADRAO", "ADMINISTRADOR" }));

        jLabel8.setText("Tipo usuário");

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(this::botaoSalvarActionPerformed);

        botaoAtualizar.setText("Atualizar");
        botaoAtualizar.addActionListener(this::botaoAtualizarActionPerformed);

        botaoDeletar.setText("Deletar");
        botaoDeletar.addActionListener(this::botaoDeletarActionPerformed);

        try {
            txtTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTel.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        txtTel.addActionListener(this::txtTelActionPerformed);

        botaoLimpar.setText("Limpar");
        botaoLimpar.addActionListener(this::botaoLimparActionPerformed);

        botaoFiltrar.setText("Buscar");
        botaoFiltrar.addActionListener(this::botaoFiltrarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(txtEmail)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(botaoSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(botaoAtualizar)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(txtNomeUsr, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(txtTel))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(txtCpf)
                            .addComponent(txtNasc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(botaoDeletar)
                                .addGap(18, 18, 18)
                                .addComponent(botaoLimpar)
                                .addGap(18, 18, 18)
                                .addComponent(botaoFiltrar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(boxUsr, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                        .addComponent(boxSexo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(111, 111, 111))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar)
                    .addComponent(botaoAtualizar)
                    .addComponent(botaoDeletar)
                    .addComponent(botaoLimpar)
                    .addComponent(botaoFiltrar))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        labelEditandoFunc.setText("Editando Funcionário #0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(labelEditandoFunc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(labelEditandoFunc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNomeUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeUsrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeUsrActionPerformed

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        if (txtNome.getText().isBlank() ||
            txtNomeUsr.getText().isBlank() ||
            txtEmail.getText().isBlank() ||
            txtCpf.getText().replaceAll("[^0-9]", "").isBlank() ||
            txtTel.getText().replaceAll("[^0-9]", "").isBlank() ||
            txtNasc.getText().replaceAll("[^0-9]", "").isBlank() ||
            boxSexo.getSelectedItem().equals("Selecione") ||
            boxUsr.getSelectedItem().equals("Selecione")) {

            Utilitarios.warningMsg("Para salvar um novo usuário todos os campos devem estar preenchidos","Atenção");
        }
        if(ControladorCRUDFuncionarios.salvar(carregarFuncionario()))
            Utilitarios.warningMsg("Usuário cadastrado com sucesso, a senha inicial é sempre definida como o cpf", "Sucesso");
        else
            Utilitarios.warningMsg("Nome de Usuário já está em uso","Atenção");
        preencherTabela(ControladorCRUDFuncionarios.search(null));
        limparFormulario();
        resetarTabela();
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void txtTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelActionPerformed

    private void botaoFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFiltrarActionPerformed
        tabela.setRowCount(0);
        preencherTabela(ControladorCRUDFuncionarios.search(carregarFiltro()));
    }//GEN-LAST:event_botaoFiltrarActionPerformed

    private void botaoLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLimparActionPerformed
        limparFormulario();
        preencherTabela(ControladorCRUDFuncionarios.search(null));
    }//GEN-LAST:event_botaoLimparActionPerformed

    private void botaoDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDeletarActionPerformed
        if(idSelecionado <= 0)
            Utilitarios.warningMsg("Nenhum funcionário selecionado", "Atenção");
        Funcionario temp = new Funcionario();
        temp.setIdFuncionario(idSelecionado);
        ControladorCRUDFuncionarios.deletar(temp);
        limparFormulario();
        resetarTabela();
    }//GEN-LAST:event_botaoDeletarActionPerformed

    private void botaoAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAtualizarActionPerformed
        if(idSelecionado <= 0)
            Utilitarios.warningMsg("Nenhum funcionário selecionado", "Atenção");
        ControladorCRUDFuncionarios.atualizar(carregarFuncionarioParaAtualizar());
        resetarTabela();
    }//GEN-LAST:event_botaoAtualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAtualizar;
    private javax.swing.JButton botaoDeletar;
    private javax.swing.JButton botaoFiltrar;
    private javax.swing.JButton botaoLimpar;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JComboBox<String> boxSexo;
    private javax.swing.JComboBox<String> boxUsr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEditandoFunc;
    private javax.swing.JTable tabelaFuncionarios;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JFormattedTextField txtNasc;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomeUsr;
    private javax.swing.JFormattedTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
