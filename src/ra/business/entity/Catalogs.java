package ra.business.entity;

import ra.business.config.InputMethods;

import java.io.Serializable;

import static ra.business.designimplement.AuthenticationService.userList;
import static ra.business.designimplement.CatalogService.catalogsList;

public class Catalogs implements Serializable {
    private String catalogId;
    private String catalogName;
    private String description;

    public Catalogs(String catalogId, String catalogName, String description) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
    }

    public Catalogs() {

    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void inputData() {
        this.catalogId = getCategoriesIdInput();
        this.catalogName = getInputCatalogsName();
        this.description = descriptionInput();
    }

    public void displayData() {
        System.out.printf("| ID: %-6s | Tên danh mục: %-9s",this.catalogId,this.catalogName);
        System.out.println("\n===========================================================");
    }

    private String getCategoriesIdInput() {
        final String regex = "^C\\w{3}$";
        while (true) {
            System.out.println("Nhập vào mã danh muc");
            String categoriesIdInput = InputMethods.getString();
            if (categoriesIdInput.matches(regex)) {
                boolean flag = true;
                for (Catalogs catalogs : catalogsList) {
                    if (catalogs != null && catalogs.getCatalogId().equals(categoriesIdInput)) {
                        flag = false;
                    }
                }
                if (flag) {
                    return categoriesIdInput;
                } else {
                    System.err.println("Id đã tồn tại, vui long nhập giá trị khác");
                }
            } else {
                System.err.println("Không đúng định dạng (C___)");
            }
        }
    }

    private String descriptionInput() {
        System.out.println("mời bạn nhập mô tả danh mục");
        return InputMethods.getString();
    }

    private String getInputCatalogsName() {
        System.out.println("Mời bạn nhập vào tên danh muc:");
        while (true) {
            String catalogsNameInput = InputMethods.getString();
            if (catalogsList.stream().anyMatch(c -> c.catalogName.equals(catalogsNameInput))) {
                System.out.println("Tên danh muc của bạn bị trùng, mời bạn nhập lại:");
            } else {
                return catalogsNameInput;
            }
        }
    }


}
