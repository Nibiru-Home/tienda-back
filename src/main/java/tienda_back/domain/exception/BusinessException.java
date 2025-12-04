package tienda_back.domain.exception;

public class BusinessException extends  RuntimeException{
    public BusinessException(String mensaje){
        super(mensaje);
    }
}