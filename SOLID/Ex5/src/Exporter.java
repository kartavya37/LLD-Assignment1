public abstract class Exporter {

    public final ExportResult export(ExportRequest req) {
        if (req == null) {
            return new ExportResult(getContentType(), new byte[0]);
        }
        
        if (req.body != null && req.body.length() > getMaxContentLength()) {
            throw new IllegalArgumentException(getFormatName() + " cannot handle content > " + getMaxContentLength() + " chars");
        }
        
        return generateExport(req);
    }

    protected abstract ExportResult generateExport(ExportRequest req);

    public int getMaxContentLength() {
        return (int)1e9; 
    }

    public abstract String getFormatName();
    public abstract String getContentType();
}