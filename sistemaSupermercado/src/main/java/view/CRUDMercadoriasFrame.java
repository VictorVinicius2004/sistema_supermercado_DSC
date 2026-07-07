package view;

import java.util.ArrayList;
import java.util.List;
import model.domain.Mercadoria;
import controller.ControladorCRUDMercadorias;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUDMercadoriasFrame extends javax.swing.JInternalFrame {
    
    private ControladorCRUDMercadorias controlador;
    private DefaultTableModel tabela;
    private int codigoSelecionado;
    
    public CRUDMercadoriasFrame() {
        initComponents();
        this.controlador = new ControladorCRUDMercadorias();
        this.tabela = (DefaultTableModel) jTable2.getModel();

        javax.swing.SpinnerNumberModel spinnerModel = new javax.swing.SpinnerNumberModel(0, 0, null, 1);
        qtdSpinner.setModel(spinnerModel);
        
        this.controlador = new ControladorCRUDMercadorias();
        this.tabela = (DefaultTableModel) jTable2.getModel();
        
        jTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = jTable2.getSelectedRow();
                if (row >= 0) preencherFormulario(row);
            }
        });

        preencherTabela(ControladorCRUDMercadorias.buscar(null));
    }
    
    private void preencherTabela(List<Mercadoria> list) {
        tabela.setRowCount(0);
        list.forEach(m -> tabela.addRow(new Object[]{
            m.getCodigo(),
            m.getNome(),
            m.getModelo(),
            m.getDescrição(),
            m.getFornecedor(),
            m.getPrecoUnitario(),
            m.getQuantidade()
        }));
    }
    
    private void preencherFormulario(int row) {
        codigoSelecionado = (int) tabela.getValueAt(row, 0);
        txtCod.setText(String.valueOf(codigoSelecionado));
        txtNome.setText((String) tabela.getValueAt(row, 1));
        txtModelo.setText((String) tabela.getValueAt(row, 2));
        txtDesc.setText((String) tabela.getValueAt(row, 3));
        txtForn.setText((String) tabela.getValueAt(row, 4));
        txtPreço.setText(String.valueOf(tabela.getValueAt(row, 5)));
        qtdSpinner.setValue(tabela.getValueAt(row, 6));
    }
    
    private void limparFormulario() {
        txtCod.setText("");
        txtNome.setText("");
        txtModelo.setText("");
        txtDesc.setText("");
        txtPreço.setText("");
        txtForn.setText("");
        qtdSpinner.setValue(0);
        codigoSelecionado = -1;
        jTable2.clearSelection();
    }
    
    private Mercadoria carregarFiltro() {
        Mercadoria m = new Mercadoria();
        m.setNome(txtNome.getText());
        m.setModelo(txtModelo.getText());
        m.setDescrição(txtDesc.getText());
        m.setFornecedor(txtForn.getText());
        m.setQuantidade((Integer) qtdSpinner.getValue());

        String precoTexto = txtPreço.getText().trim().replaceAll(",", ".");
        try {
            m.setPrecoUnitario(Double.parseDouble(precoTexto));
        } catch (Exception e) {
            m.setPrecoUnitario(null);
        }

        return m;
    }
    
    private Mercadoria carregarMercadoriaParaAtualizar() {
        Mercadoria m = carregarFiltro();
        m.setCodigo(codigoSelecionado);
        return m;
    }
    
    private void resetarTabela() {
        tabela.setRowCount(0);
        preencherTabela(ControladorCRUDMercadorias.buscar(null));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tabelaNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        tabelaModelo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        tabelaDesc = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        tabelaPreço = new javax.swing.JLabel();
        txtPreço = new javax.swing.JTextField();
        tabelaQtd = new javax.swing.JLabel();
        qtdSpinner = new javax.swing.JSpinner();
        tabelaForn = new javax.swing.JLabel();
        txtForn = new javax.swing.JTextField();
        buttonAtt = new javax.swing.JButton();
        buttonSalvar = new javax.swing.JButton();
        buttonDel = new javax.swing.JButton();
        buttonClear = new javax.swing.JButton();
        buttonBuscar = new javax.swing.JButton();
        txtCod = new javax.swing.JTextField();
        tabelaCod = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(744, 521));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelaNome.setText("Nome");

        txtNome.addActionListener(this::txtNomeActionPerformed);

        tabelaModelo.setText("Modelo");

        txtModelo.addActionListener(this::txtModeloActionPerformed);

        tabelaDesc.setText("Descrição");

        txtDesc.addActionListener(this::txtDescActionPerformed);

        tabelaPreço.setText("Preço");

        txtPreço.addActionListener(this::txtPreçoActionPerformed);

        tabelaQtd.setText("Quantidade");

        tabelaForn.setText("Fornecedor");

        txtForn.addActionListener(this::txtFornActionPerformed);

        buttonAtt.setText("Atualizar");
        buttonAtt.addActionListener(this::buttonAttActionPerformed);

        buttonSalvar.setText("Salvar");
        buttonSalvar.addActionListener(this::buttonSalvarActionPerformed);

        buttonDel.setText("Desativar");
        buttonDel.addActionListener(this::buttonDelActionPerformed);

        buttonClear.setText("Limpar");
        buttonClear.addActionListener(this::buttonClearActionPerformed);

        buttonBuscar.setText("Buscar");
        buttonBuscar.addActionListener(this::buttonBuscarActionPerformed);

        txtCod.addActionListener(this::txtCodActionPerformed);

        tabelaCod.setText("Código");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tabelaPreço)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tabelaCod)
                        .addGap(109, 109, 109))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPreço, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDesc)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tabelaNome)
                            .addComponent(tabelaModelo)
                            .addComponent(tabelaDesc)
                            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(txtModelo))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtForn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tabelaForn)
                            .addComponent(qtdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tabelaQtd))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(buttonSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAtt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBuscar)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabelaNome)
                    .addComponent(tabelaPreço)
                    .addComponent(tabelaCod)
                    .addComponent(tabelaQtd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPreço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qtdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tabelaModelo)
                    .addComponent(tabelaForn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabelaDesc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAtt)
                    .addComponent(buttonSalvar)
                    .addComponent(buttonDel)
                    .addComponent(buttonClear)
                    .addComponent(buttonBuscar))
                .addContainerGap())
        );

        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane2.setName(""); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Modelo", "Descrição", "Fornecedor", "Preço", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPreçoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPreçoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPreçoActionPerformed

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescActionPerformed

    private void txtModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModeloActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFornActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFornActionPerformed

    private void buttonAttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAttActionPerformed
        if (codigoSelecionado <= 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma mercadoria selecionada", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double preco = Double.parseDouble(txtPreço.getText().trim().replaceAll(",", "."));
            if (preco < 0) {
                JOptionPane.showMessageDialog(this, "O preço unitário não pode ser negativo.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor numérico válido para o preço.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ControladorCRUDMercadorias.atualizar(carregarMercadoriaParaAtualizar());
        JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
        resetarTabela();
    }//GEN-LAST:event_buttonAttActionPerformed

    private void buttonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvarActionPerformed
        if (txtNome.getText().isBlank() ||
            txtModelo.getText().isBlank() ||
            txtDesc.getText().isBlank() ||
            txtPreço.getText().isBlank() ||
            txtForn.getText().isBlank()) {

            JOptionPane.showMessageDialog(this, "Para salvar uma mercadoria todos os campos devem estar preenchidos", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double preco = Double.parseDouble(txtPreço.getText().trim().replaceAll(",", "."));
            if (preco < 0) {
                JOptionPane.showMessageDialog(this, "O preço unitário não pode ser negativo.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor numérico válido para o preço.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (ControladorCRUDMercadorias.salvar(carregarFiltro())) {
            JOptionPane.showMessageDialog(this, "Mercadoria cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        
        preencherTabela(ControladorCRUDMercadorias.buscar(null));
        limparFormulario();
        resetarTabela();
    }//GEN-LAST:event_buttonSalvarActionPerformed

    private void buttonDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDelActionPerformed
        if (codigoSelecionado <= 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma mercadoria selecionada", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Desativar \"" + txtNome.getText() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Mercadoria temp = new Mercadoria();
            temp.setCodigo(codigoSelecionado);
            ControladorCRUDMercadorias.desativar(temp);
            limparFormulario();
            resetarTabela();
        }
    }//GEN-LAST:event_buttonDelActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        limparFormulario();
        preencherTabela(ControladorCRUDMercadorias.buscar(null));
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
        String cod = txtCod.getText().trim();
        if (cod.isBlank()) { JOptionPane.showMessageDialog(this, "Digite um código!"); return; }

        Mercadoria filtro = new Mercadoria();
        filtro.setCodigo(Integer.parseInt(cod));
        List<Mercadoria> lista = ControladorCRUDMercadorias.buscar(filtro);
        tabela.setRowCount(0);
        preencherTabela(lista);

        if (!lista.isEmpty()) {
            preencherFormulario(0);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma mercadoria encontrada com esse código.");
            limparFormulario();
        }
    }//GEN-LAST:event_buttonBuscarActionPerformed

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAtt;
    private javax.swing.JButton buttonBuscar;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonDel;
    private javax.swing.JButton buttonSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JSpinner qtdSpinner;
    private javax.swing.JLabel tabelaCod;
    private javax.swing.JLabel tabelaDesc;
    private javax.swing.JLabel tabelaForn;
    private javax.swing.JLabel tabelaModelo;
    private javax.swing.JLabel tabelaNome;
    private javax.swing.JLabel tabelaPreço;
    private javax.swing.JLabel tabelaQtd;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtForn;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPreço;
    // End of variables declaration//GEN-END:variables
}
