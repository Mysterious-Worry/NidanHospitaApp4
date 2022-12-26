package in.co.okservices.nidanhospitaapp4.data_models;

public class patient_model {
    public String _id, sr_no, checked, type, color, date, time, amount, name, age;

    public patient_model(String _id, String sr_no, String checked, String type, String color, String date, String time, String amount,String name, String age) {
        this._id = _id;
        this.sr_no = sr_no;
        this.checked = checked;
        this.type = type;
        this.color = color;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.name = name;
        this.age = age;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}