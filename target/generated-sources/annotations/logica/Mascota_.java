package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.Dueno;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-08T02:50:38", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Mascota.class)
public class Mascota_ { 

    public static volatile SingularAttribute<Mascota, Dueno> dueno;
    public static volatile SingularAttribute<Mascota, String> raza;
    public static volatile SingularAttribute<Mascota, String> color;
    public static volatile SingularAttribute<Mascota, String> alergico;
    public static volatile SingularAttribute<Mascota, String> observaciones;
    public static volatile SingularAttribute<Mascota, Integer> numeroCliente;
    public static volatile SingularAttribute<Mascota, String> nombre;
    public static volatile SingularAttribute<Mascota, String> atencionEspecial;

}