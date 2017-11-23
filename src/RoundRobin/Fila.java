package RoundRobin;


/**
 *
 * @author Thauan Trindade
 */
public class Fila {

    int tamanho;
    No node;

    public void equeue(Processo p) {
        if (isEmpty()) {
            node = new No(p);
            tamanho++;
        } else {
            No aux = node;
            for (int i = 0; i < tamanho; i++) {
                if (aux.proximo != null) {
                    aux = aux.proximo;
                } else {
                    break;
                }
            }
            aux.proximo = new No(p);

            tamanho++;
        }
    }

    public Processo dequeue() {
        if (isEmpty()) {
            return null;
        } else {
            No aux = node;
            node = node.proximo;
            tamanho--;
            return aux.valor;
        }
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public String print() {
        if (isEmpty()) {
            return "Nao ha processos na fila!";
        }
        String saida = "";
        No aux = node;
        for (int i = 0; i < tamanho; i++) {
            saida += aux.valor.pid + " (" + aux.valor.duracao + ") ";
            aux = aux.proximo;
        }

        return saida;
    }
}
