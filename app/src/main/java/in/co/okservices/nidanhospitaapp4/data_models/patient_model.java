package in.co.okservices.nidanhospitaapp4.data_models;

public class patient_model {
    String _id, sr_no, checked, type, date, time, name, age;

    public patient_model() { }

    public patient_model(String _id, String sr_no, String checked, String type, String date, String time, String name, String age) {
        this._id = _id;
        this.sr_no = sr_no;
        this.checked = checked;
        this.type = type;
        this.date = date;
        this.time = time;
        this.name = name;
        this.age = age;
    }

    public String get_id() {
        return _id;
    }

    public String getSr_no() {
        return sr_no;
    }

    public String getChecked() {
        return checked;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
