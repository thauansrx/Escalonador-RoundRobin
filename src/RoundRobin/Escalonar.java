package RoundRobin;


/**
 *
 * @author Thauan Trindade
 */
public class Escalonar {

    public static void main(String[] args) throws InterruptedException {

        int quantum = 4;
        int quantumCont = 0;
        int cont = 0;
        int qtdProcessos;

        Processo cpuP = null;

        String print1;
        String print2;
        String print3;

        Fila fila = new Fila();
        Processo[] processo = new Processo[5];

        //cria os processos
        processo[0] = new Processo("P1", 9, 10, new int[]{2, 4, 6, 8});
        processo[1] = new Processo("P2", 10, 4, new int[]{5});
        processo[2] = new Processo("P3", 5, 0, new int[]{2});
        processo[3] = new Processo("P4", 7, 1, new int[]{3, 6});
        processo[4] = new Processo("P5", 2, 17, null);

        qtdProcessos = processo.length;

        System.out.println("*************************************");
        System.out.println("*******Escalonador Round-Robin*******");
        System.out.println("*************************************");
        print1 = "Tempo " + cont;
        print2 = "FILA: ";
        print3 = "CPU: ";

        for (Processo p : processo) {
            if (p.chegada == cont) {
                fila.equeue(p);
                print1 += " Chegada do Processo " + p.pid;
            }
        }
        do {

            //troca de contexto
            //uma thread pra add eles a fila baseada no tempo ao utilizar a cpu
            if (cpuP == null) {
                cpuP = fila.dequeue();
            }

            print2 += fila.print();
            print3 += cpuP.pid + " (" + cpuP.duracao + ")";
            System.out.println(print1);
            System.out.println(print2);
            System.out.println(print3);
            System.out.println(" ");

            Thread.sleep(500);
            cont++;

            print1 = "Tempo " + cont;
            print2 = "FILA: ";
            print3 = "CPU: ";

            if (cpuP != null) {

                cpuP.duracao -= 1;
                cpuP.cpuUsing++;
                quantumCont++;

                if (cpuP.duracao == 0) {

                    print1 += " Fim do processo " + cpuP.pid;
                    cpuP = fila.dequeue();
                    quantumCont = 0;
                    qtdProcessos--;

                } else if (quantumCont == quantum) {

                    print1 += " Fim do quantum " + cpuP.pid;
                    quantumCont = 0;
                    fila.equeue(cpuP);
                    cpuP = fila.dequeue();

                } else {

                    for (int i = 0; i < processo.length; i++) {

                        if (processo[i].chegada == cont) {
                            fila.equeue(processo[i]);
                            print1 += " Chegada do processo " + processo[i].pid;
                        }
                    }

                    if (cpuP.opIO != null) {

                        for (int i = 0; i < cpuP.opIO.length; i++) {

                            if (cpuP.opIO[i] == cpuP.cpuUsing) {
                                print1 += " Operacao de IO " + cpuP.pid;
                                fila.equeue(cpuP);

                                cpuP = fila.dequeue();
                                quantumCont = 0;
                                break;
                            }
                        }
                    }
                }
            }

        } while (qtdProcessos != 0);
    }
}
