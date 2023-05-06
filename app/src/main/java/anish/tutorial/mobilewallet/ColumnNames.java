package anish.tutorial.mobilewallet;

public class ColumnNames {
    String remarks, Date,id;
    int cb,pb,status,add;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ColumnNames() {
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public int getPb() {
        return pb;
    }

    public void setPb(int pb) {
        this.pb = pb;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }
}
