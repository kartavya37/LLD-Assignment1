public interface InvoiceRepo {

    public void save(String name , String content);
    public int countLines(String name);
    
}
