package model.domain;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class Venda {
    private int idVenda;
    private LocalDateTime datavenda;
    private Double valorTotal;
    private List<ItemVenda> itens;
    private LocalDateTime dataVenda;
    
    public Venda() {
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
        this.dataVenda = LocalDateTime.now();
    }
    
    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
        if (item.getSubtotal() != null) {
            this.valorTotal += item.getSubtotal();
        }
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public LocalDateTime getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(LocalDateTime datavenda) {
        this.datavenda = datavenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }
}
