package ra.business.designimplement;

import ra.business.config.IO_file;
import ra.business.entity.Catalogs;

import java.util.ArrayList;
import java.util.List;

public class CatalogService {
    public static List<Catalogs> catalogsList;
    static {
        catalogsList= IO_file.readObjFromFile(IO_file.CATALOGS_PATH);
    }

}
