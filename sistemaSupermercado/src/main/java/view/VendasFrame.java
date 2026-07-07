package view;

import controller.ControladorCRUDMercadorias;
import controller.ControladorVendas;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.domain.Funcionario;
import model.domain.ItemVenda;
import model.domain.Mercadoria;
import model.domain.Venda;
import utilitarios.Utilitarios;

public class VendasFrame extends javax.swing.JInternalFrame {

    private Funcionario funcionarioLogado;
    private Venda carrinho;
    private DefaultTableModel modeloTabelaMercadorias;
    private DefaultTableModel modeloTabelaCarrinho;
    private Mercadoria mercadoriaSelecionada;
    private ButtonGroup grupoPagamento;

    public VendasFrame(Funcionario funcionario) {
        initComponents();

        this.funcionarioLogado = funcionario;
        this.carrinho = new Venda();
        
        grupoPagamento = new ButtonGroup();
        grupoPagamento.add(buttonDinheiro);
        grupoPagamento.add(buttonCredito);
        grupoPagamento.add(buttonDebito);
        buttonDinheiro.setSelected(true);

        qtdSpinner.setValue(1);

        this.modeloTabelaMercadorias = (DefaultTableModel) tabelaMercadorias.getModel();
        this.modeloTabelaCarrinho = (DefaultTableModel) tabelaCarrinho.getModel();

        tabelaMercadorias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tabelaMercadorias.getSelectedRow();
                if (row >= 0) {
                    int codigo = (int) modeloTabelaMercadorias.getValueAt(row, 0);
                    mercadoriaSelecionada = ControladorVendas.buscarMercadoriaPorCodigo(codigo);
                    preencherCamposMercadoria(mercadoriaSelecionada);
                }
            }
        });

        atualizarTabelaMercadorias(ControladorCRUDMercadorias.buscar(null));
    }

    VendasFrame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void preencherCamposMercadoria(Mercadoria m) {
        if (m != null) {
            txtCod.setText(String.valueOf(m.getCodigo()));
            txtNome.setText(m.getNome());
            txtModelo.setText(m.getModelo());
            txtPreço.setText(String.format("%.2f", m.getPrecoUnitario()));
            txtDesc.setText(m.getDescrição() != null ? m.getDescrição() : "");
            qtdSpinner.setValue(1);
        }
    }

    private void atualizarTabelaMercadorias(List<Mercadoria> lista) {
        modeloTabelaMercadorias.setRowCount(0);
        for (Mercadoria m : lista) {
            modeloTabelaMercadorias.addRow(new Object[]{
                m.getCodigo(),
                m.getNome(),
                m.getQuantidade(),
                m.getModelo(),
                m.getDescrição(),
                m.getPrecoUnitario()
            });
        }
    }

    private void atualizarTabelaCarrinho() {
        modeloTabelaCarrinho.setRowCount(0);
        for (ItemVenda item : carrinho.getItens()) {
            modeloTabelaCarrinho.addRow(new Object[]{
                item.getMercadoria().getCodigo(),
                item.getMercadoria().getNome(),
                item.getQuantidade(),
                item.getMercadoria().getPrecoUnitario(),
                item.getSubtotal()
            });
        }
        lblTotal.setText(String.format("Total: R$ %.2f", carrinho.getValorTotal()));
    }

    private void limparCampos() {
        txtCod.setText("");
        txtNome.setText("");
        txtModelo.setText("");
        txtPreço.setText("");
        txtDesc.setText("");
        qtdSpinner.setValue(1);
        mercadoriaSelecionada = null;
        tabelaMercadorias.clearSelection();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tabelaNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        tabelaCod = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        tabelaTipo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        tabelaDesc = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        tabelaPreço = new javax.swing.JLabel();
        txtPreço = new javax.swing.JTextField();
        buttonBuscar = new javax.swing.JButton();
        buttonPagar = new javax.swing.JButton();
        buttonAddCarrinho = new javax.swing.JButton();
        buttonRemoveCarrinho = new javax.swing.JButton();
        tabelaQuantidade = new javax.swing.JLabel();
        qtdSpinner = new javax.swing.JSpinner();
        tabelaPagamento = new javax.swing.JLabel();
        buttonDinheiro = new javax.swing.JRadioButton();
        buttonCredito = new javax.swing.JRadioButton();
        buttonDebito = new javax.swing.JRadioButton();
        buttonLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaMercadorias = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCarrinho = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(744, 521));

        tabelaNome.setText("Nome");

        tabelaCod.setText("Código");

        tabelaTipo.setText("Modelo");

        tabelaDesc.setText("Descrição");

        txtDesc.addActionListener(this::txtDescActionPerformed);

        tabelaPreço.setText("Preço");

        buttonBuscar.setText("Buscar");
        buttonBuscar.addActionListener(this::buttonBuscarActionPerformed);

        buttonPagar.setText("Pagar");
        buttonPagar.addActionListener(this::buttonPagarActionPerformed);

        buttonAddCarrinho.setText("Adicionar ao Carrinho");
        buttonAddCarrinho.addActionListener(this::buttonAddCarrinhoActionPerformed);

        buttonRemoveCarrinho.setText("Remover do Carrinho");
        buttonRemoveCarrinho.addActionListener(this::buttonRemoveCarrinhoActionPerformed);

        tabelaQuantidade.setText("Quantidade");

        tabelaPagamento.setText("Forma de Pagamento");

        buttonDinheiro.setText("Dinheiro");
        buttonDinheiro.addActionListener(this::buttonDinheiroActionPerformed);

        buttonCredito.setText("Cartão de Crédito");
        buttonCredito.addActionListener(this::buttonCreditoActionPerformed);

        buttonDebito.setText("Cartão de Débito");
        buttonDebito.addActionListener(this::buttonDebitoActionPerformed);

        buttonLimpar.setText("Limpar");
        buttonLimpar.addActionListener(this::buttonLimparActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonAddCarrinho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonRemoveCarrinho)
                        .addGap(18, 18, 18)
                        .addComponent(buttonBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(buttonLimpar)
                        .addGap(20, 20, 20)
                        .addComponent(buttonPagar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tabelaTipo)
                            .addComponent(tabelaNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtPreço, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(qtdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tabelaPreço)
                                        .addGap(111, 111, 111)
                                        .addComponent(tabelaQuantidade)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tabelaCod)
                                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(54, 54, 54))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(tabelaDesc)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtDesc))
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tabelaPagamento)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonDinheiro)
                                .addGap(39, 39, 39)
                                .addComponent(buttonCredito)
                                .addGap(18, 18, 18)
                                .addComponent(buttonDebito)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tabelaNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tabelaCod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tabelaTipo)
                            .addComponent(tabelaDesc)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tabelaPreço)
                            .addComponent(tabelaQuantidade))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPreço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qtdSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tabelaPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonDinheiro)
                    .addComponent(buttonCredito)
                    .addComponent(buttonDebito))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBuscar)
                    .addComponent(buttonPagar)
                    .addComponent(buttonAddCarrinho)
                    .addComponent(buttonRemoveCarrinho)
                    .addComponent(buttonLimpar))
                .addGap(25, 25, 25))
        );

        tabelaMercadorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Quantidade", "Modelo", "Descrição", "Preço Unitário"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaMercadorias);

        tabelaCarrinho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Quantidade", "Preço Unitário", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaCarrinho);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTotal.setText("Total: R$ 0,00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuscarActionPerformed
       String codStr = txtCod.getText().trim();
        String nome = txtNome.getText().trim();

        if (!codStr.isEmpty()) {
            try {
                int codigo = Integer.parseInt(codStr);
                Mercadoria m = ControladorVendas.buscarMercadoriaPorCodigo(codigo);
                if (m != null) {
                    atualizarTabelaMercadorias(List.of(m));
                    preencherCamposMercadoria(m);
                    mercadoriaSelecionada = m;
                } else {
                    Utilitarios.warningMsg("Mercadoria não encontrada.", "Busca");
                }
            } catch (NumberFormatException e) {
                Utilitarios.warningMsg("Código inválido.", "Erro");
            }
        } else if (!nome.isEmpty()) {
            List<Mercadoria> lista = ControladorVendas.buscarMercadoriasPorNome(nome);
            atualizarTabelaMercadorias(lista);
        } else {
            atualizarTabelaMercadorias(ControladorCRUDMercadorias.buscar(null));
        }
    }//GEN-LAST:event_buttonBuscarActionPerformed

    private void buttonPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPagarActionPerformed
        if (carrinho.getItens().isEmpty()) {
            Utilitarios.warningMsg("O carrinho está vazio.", "Atenção");
            return;
        }

        String tipoPagamento = "";
        if (buttonDinheiro.isSelected()) {
            tipoPagamento = "Dinheiro";
        } else if (buttonCredito.isSelected()) {
            tipoPagamento = "Cartão de Crédito";
        } else if (buttonDebito.isSelected()) {
            tipoPagamento = "Cartão de Débito";
        } else {
            Utilitarios.warningMsg("Selecione uma forma de pagamento.", "Atenção");
            return;
        }

        double valorPago = 0;
        double troco = 0;

        if (tipoPagamento.equals("Dinheiro")) {
            String valorStr = JOptionPane.showInputDialog(this, 
                    String.format("Valor total: R$ %.2f\nDigite o valor pago pelo cliente:", carrinho.getValorTotal()));
            
            if (valorStr == null || valorStr.trim().isEmpty()) return;
            
            try {
                valorPago = Double.parseDouble(valorStr.replace(",", "."));
                if (valorPago < carrinho.getValorTotal()) {
                    Utilitarios.warningMsg("O valor pago é insuficiente.", "Erro");
                    return;
                }
                troco = ControladorVendas.calcularTroco(carrinho.getValorTotal(), valorPago);
                JOptionPane.showMessageDialog(this, String.format("Troco a ser devolvido: R$ %.2f", troco), "Troco", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                Utilitarios.warningMsg("Valor digitado é inválido.", "Erro");
                return;
            }
        } else {
            valorPago = carrinho.getValorTotal(); 
        }

        boolean sucesso = ControladorVendas.finalizarVenda(carrinho, funcionarioLogado.getIdFuncionario(), tipoPagamento, valorPago);

        if (sucesso) {
            String comprovante = ControladorVendas.gerarComprovante(carrinho, tipoPagamento, valorPago, troco);
            JOptionPane.showMessageDialog(this, comprovante, "Comprovante", JOptionPane.INFORMATION_MESSAGE);
            
            carrinho = new Venda();
            atualizarTabelaCarrinho();
            atualizarTabelaMercadorias(ControladorCRUDMercadorias.buscar(null));
            limparCampos();
        } else {
            Utilitarios.warningMsg("Erro ao registrar a venda no banco de dados.", "Erro Crítico");
        }
    }//GEN-LAST:event_buttonPagarActionPerformed

    private void buttonAddCarrinhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddCarrinhoActionPerformed
        if (mercadoriaSelecionada == null) {
            Utilitarios.warningMsg("Selecione uma mercadoria na tabela primeiro.", "Atenção");
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdSpinner.getValue().toString());
            if (quantidade <= 0) {
                Utilitarios.warningMsg("A quantidade deve ser maior que zero.", "Atenção");
                return;
            }
        } catch (Exception e) {
            Utilitarios.warningMsg("Quantidade inválida no spinner.", "Erro");
            return;
        }

        boolean sucesso = ControladorVendas.adicionarAoCarrinho(carrinho, mercadoriaSelecionada, quantidade);

        if (sucesso) {
            atualizarTabelaCarrinho();
            limparCampos();
        } else {
            Utilitarios.warningMsg("Estoque insuficiente para esta quantidade.", "Erro de Estoque");
        }
    }//GEN-LAST:event_buttonAddCarrinhoActionPerformed

    private void buttonRemoveCarrinhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRemoveCarrinhoActionPerformed
        int row = tabelaCarrinho.getSelectedRow();
        if (row >= 0) {
            ControladorVendas.removerDoCarrinho(carrinho, row);
            atualizarTabelaCarrinho();
        } else {
            Utilitarios.warningMsg("Selecione um item na tabela do CARRINHO para remover.", "Atenção");
        }
    }//GEN-LAST:event_buttonRemoveCarrinhoActionPerformed

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescActionPerformed

    private void buttonDinheiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDinheiroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonDinheiroActionPerformed

    private void buttonCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonCreditoActionPerformed

    private void buttonDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDebitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonDebitoActionPerformed

    private void buttonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLimparActionPerformed
        limparCampos();
        atualizarTabelaMercadorias(controller.ControladorCRUDMercadorias.buscar(null));
    }//GEN-LAST:event_buttonLimparActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddCarrinho;
    private javax.swing.JButton buttonBuscar;
    private javax.swing.JRadioButton buttonCredito;
    private javax.swing.JRadioButton buttonDebito;
    private javax.swing.JRadioButton buttonDinheiro;
    private javax.swing.JButton buttonLimpar;
    private javax.swing.JButton buttonPagar;
    private javax.swing.JButton buttonRemoveCarrinho;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSpinner qtdSpinner;
    private javax.swing.JTable tabelaCarrinho;
    private javax.swing.JLabel tabelaCod;
    private javax.swing.JLabel tabelaDesc;
    private javax.swing.JTable tabelaMercadorias;
    private javax.swing.JLabel tabelaNome;
    private javax.swing.JLabel tabelaPagamento;
    private javax.swing.JLabel tabelaPreço;
    private javax.swing.JLabel tabelaQuantidade;
    private javax.swing.JLabel tabelaTipo;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPreço;
    // End of variables declaration//GEN-END:variables
}
