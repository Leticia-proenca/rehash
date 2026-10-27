import java.util.Random;

public class Main {

    static int[] tabela = new int[100];
    static boolean[] ocupada = new boolean[100];
    static int[] chaves = new int[100];
    static int[] colisoes = new int[100];
    static int qtd = 0;
    static int totalColisoes = 0;

    static int hash(int chave) {
        return chave % 100;
    }

    static int rehash(int pos) {
        return (pos + 1) % 100;
    }

    static int buscar(int chave) {
        int pos = hash(chave);
        while (ocupada[pos]) {
            if (tabela[pos] == chave) {
                return pos;
            }
            pos = rehash(pos);
        }
        return -1;
    }

    static int inserir(int chave) {
        if (qtd >= 90) {
            return -1;
        }
        int pos = hash(chave);
        int c = 0;
        while (ocupada[pos]) {
            if (tabela[pos] == chave) {
                return 0;
            }
            c++;
            pos = rehash(pos);
        }
        tabela[pos] = chave;
        ocupada[pos] = true;
        chaves[qtd] = chave;
        colisoes[qtd] = c;
        totalColisoes += c;
        qtd++;
        return 1;
    }

    public static void main(String[] args) {
        Random random = new Random(1090);
        int tentativas = 0;

        while (tentativas < 100) {
            int resultado = inserir(random.nextInt(10000));
            if (resultado != 0) {
                tentativas++;
            }
        }

        System.out.println("Total de colisoes: " + totalColisoes);

        int[] top = new int[3];
        for (int k = 0; k < 3; k++) {
            int melhor = 0;
            for (int i = 0; i < qtd; i++) {
                if (colisoes[i] > colisoes[melhor]) {
                    melhor = i;
                }
            }
            top[k] = chaves[melhor];
            System.out.println("Mais colidiu: " + chaves[melhor] + " com " + colisoes[melhor] + " colisoes");
            colisoes[melhor] = -1;
        }

        System.out.print("Posicoes livres: ");
        for (int i = 0; i < 100; i++) {
            if (!ocupada[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        for (int k = 0; k < 3; k++) {
            int pos = buscar(top[k]);
            System.out.println("Busca " + top[k] + ": posicao " + pos);
        }
    }
}
