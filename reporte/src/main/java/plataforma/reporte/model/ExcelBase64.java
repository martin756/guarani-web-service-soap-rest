package plataforma.reporte.model;

public class ExcelBase64 {
    private String bytes;

    public ExcelBase64() {
    }

    public ExcelBase64(String bytes) {
        this.bytes = bytes;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
}
