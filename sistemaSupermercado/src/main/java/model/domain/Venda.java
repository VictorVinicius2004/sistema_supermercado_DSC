package model.domain;

import java.time.LocalDateTime;

public class Venda {
    private int idVenda;
    private LocalDateTime datavenda;
    private Double valorTotal;

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
}
