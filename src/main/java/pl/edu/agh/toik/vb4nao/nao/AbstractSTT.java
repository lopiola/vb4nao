package pl.edu.agh.toik.vb4nao.nao;

/**
 * Created by lopiola on 18.05.14.
 */
public interface AbstractSTT {
    public void setDictionary(String[] dictionary);
    public String pollWord();
    public void cleanUp();
}
