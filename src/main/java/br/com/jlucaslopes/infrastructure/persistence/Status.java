package br.com.jlucaslopes.infrastructure.persistence;


import br.com.jlucaslopes.application.exceptions.StatusNaoPodeMudarException;

public enum Status {

    RECEBIDA,
    EM_DIAGNOSTICO,
    AGUARDANDO_APROVACAO,
    EM_EXECUCAO,
    FINALIZADA,
    ENTREGUE,
    CANCELADA;

    public Status avancar() {
        return switch (this) {
            case RECEBIDA -> EM_DIAGNOSTICO;
            case EM_DIAGNOSTICO -> AGUARDANDO_APROVACAO;
            case AGUARDANDO_APROVACAO -> EM_EXECUCAO;
            case EM_EXECUCAO -> FINALIZADA;
            case FINALIZADA -> ENTREGUE;
            default -> throw new StatusNaoPodeMudarException("Ordem nao pode ser avançada"); // Se já estiver em ENTREGUE ou CANCELADA, não avança
        };
    }

    public Status retornar() {
        return switch (this) {
            case AGUARDANDO_APROVACAO, FINALIZADA, EM_EXECUCAO -> EM_DIAGNOSTICO;
            default -> throw new StatusNaoPodeMudarException("Ordem nao pode mais retornar");
        };
    }
}
