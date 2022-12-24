package in.co.okservices.nidanhospitaapp4.data_models;

public class day_record_madel {
    String _id, date, total_patients, normal, emergency, paper_valid,
            emergency_paper_valid, discount, cancel, add_amount, total_amount_collected;

    public day_record_madel() { }

    public day_record_madel(String _id, String date, String total_patients, String normal, String emergency, String paper_valid, String emergency_paper_valid, String discount, String cancel, String add_amount, String total_amount_collected) {
        this._id = _id;
        this.date = date;
        this.total_patients = total_patients;
        this.normal = normal;
        this.emergency = emergency;
        this.paper_valid = paper_valid;
        this.emergency_paper_valid = emergency_paper_valid;
        this.discount = discount;
        this.cancel = cancel;
        this.add_amount = add_amount;
        this.total_amount_collected = total_amount_collected;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_patients() {
        return total_patients;
    }

    public void setTotal_patients(String total_patients) {
        this.total_patients = total_patients;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getPaper_valid() {
        return paper_valid;
    }

    public void setPaper_valid(String paper_valid) {
        this.paper_valid = paper_valid;
    }

    public String getEmergency_paper_valid() {
        return emergency_paper_valid;
    }

    public void setEmergency_paper_valid(String emergency_paper_valid) {
        this.emergency_paper_valid = emergency_paper_valid;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getAdd_amount() {
        return add_amount;
    }

    public void setAdd_amount(String add_amount) {
        this.add_amount = add_amount;
    }

    public String getTotal_amount_collected() {
        return total_amount_collected;
    }

    public void setTotal_amount_collected(String total_amount_collected) {
        this.total_amount_collected = total_amount_collected;
    }
}
