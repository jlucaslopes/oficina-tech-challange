package br.com.jlucaslopes.infrastructure.persistence.ordemservico;

import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoEntity;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class OrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private VeiculoEntity veiculo;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<ServicoEntity> servicos;

    public OrdemServicoEntity() {
        this.status = Status.RECEBIDA;
        this.dataInicio = OffsetDateTime.now();
    }

    public OrdemServicoEntity(Long id, String descricao, OffsetDateTime dataInicio, OffsetDateTime dataFim, Status status, VeiculoEntity veiculo, List<ServicoEntity> servicos) {
        this.id = id;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.veiculo = veiculo;
        this.servicos = servicos;
    }

    public VeiculoEntity getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoEntity veiculo) {
        this.veiculo = veiculo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public OffsetDateTime getDataInicio() {
        return dataInicio;
    }

    public OffsetDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(OffsetDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ServicoEntity> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoEntity> servicos) {
        this.servicos = servicos;
    }
}
