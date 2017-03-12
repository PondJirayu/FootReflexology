package jirayu.pond.footreflexology.manager;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by lp700 on 23/10/2559.
 */

public class StringsManager {

    private String word;

    public StringsManager() {

    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordNoneNumberAndNoneWhiteSpace() {
        return getWord().substring(3).trim();
    }

    public String getChangeBirthDate() {
        return  getWord().substring(8) + "-" + getWord().substring(5, 7) + "-" + getWord().substring(0, 4);
    }

    public String getDay() {
        return getWord().substring(8);
    }

    public String getMonth() {
        return getWord().substring(5, 7);
    }

    public String getYear() {
        return getWord().substring(0, 4);
    }

    public int getProvinceId() {
        ArrayList<String> arrProvinces = new ArrayList<>();
        arrProvinces.add("กรุงเทพมหานคร");
        arrProvinces.add("กระบี่");
        arrProvinces.add("กาญจนบุรี");
        arrProvinces.add("กาฬสินธุ์");
        arrProvinces.add("กำแพงเพชร");
        arrProvinces.add("ขอนแก่น");
        arrProvinces.add("จันทบุรี");
        arrProvinces.add("ฉะเชิงเทรา");
        arrProvinces.add("ชลบุรี");
        arrProvinces.add("ชัยนาท");
        arrProvinces.add("ชัยภูมิ");
        arrProvinces.add("ชุมพร");
        arrProvinces.add("เชียงราย");
        arrProvinces.add("เชียงใหม่");
        arrProvinces.add("ตรัง");
        arrProvinces.add("ตราด");
        arrProvinces.add("ตาก");
        arrProvinces.add("นครนายก");
        arrProvinces.add("นครปฐม");
        arrProvinces.add("นครราชสีมา");
        arrProvinces.add("นครศรีธรรมราช");
        arrProvinces.add("นครสวรรค์");
        arrProvinces.add("นนทบุรี");
        arrProvinces.add("นราธิวาส");
        arrProvinces.add("น่าน");
        arrProvinces.add("บึงกาฬ");
        arrProvinces.add("บุรีรัมย์");
        arrProvinces.add("ปทุมธานี");
        arrProvinces.add("ประจวบคีรีขันธ์");
        arrProvinces.add("ปราจีนบุรี");
        arrProvinces.add("ปัตตานี");
        arrProvinces.add("พระนครศรีอยุธยา");
        arrProvinces.add("พังงา");
        arrProvinces.add("พัทลุง");
        arrProvinces.add("พิจิตร");
        arrProvinces.add("พิษณุโลก");
        arrProvinces.add("เพชรบุรี");
        arrProvinces.add("เพชรบูรณ์");
        arrProvinces.add("แพร่");
        arrProvinces.add("พะเยา");
        arrProvinces.add("ภูเก็ต");
        arrProvinces.add("มหาสารคาม");
        arrProvinces.add("มุกดาหาร");
        arrProvinces.add("แม่ฮ่องสอน");
        arrProvinces.add("ยะลา");
        arrProvinces.add("ยโสธร");
        arrProvinces.add("ร้อยเอ็ด");
        arrProvinces.add("ระนอง");
        arrProvinces.add("ระยอง");
        arrProvinces.add("ราชบุรี");
        arrProvinces.add("ลพบุรี");
        arrProvinces.add("ลำปาง");
        arrProvinces.add("ลำพูน");
        arrProvinces.add("เลย");
        arrProvinces.add("ศรีสะเกษ");
        arrProvinces.add("สกลนคร");
        arrProvinces.add("สงขลา");
        arrProvinces.add("สตูล");
        arrProvinces.add("สมุทรปราการ");
        arrProvinces.add("สมุทรสงคราม");
        arrProvinces.add("สมุทรสาคร");
        arrProvinces.add("สระแก้ว");
        arrProvinces.add("สระบุรี");
        arrProvinces.add("สิงห์บุรี");
        arrProvinces.add("สุโขทัย");
        arrProvinces.add("สุพรรณบุรี");
        arrProvinces.add("สุราษฎร์ธานี");
        arrProvinces.add("สุรินทร์");
        arrProvinces.add("หนองคาย");
        arrProvinces.add("หนองบัวลำภู");
        arrProvinces.add("อ่างทอง");
        arrProvinces.add("อุดรธานี");
        arrProvinces.add("อุทัยธานี");
        arrProvinces.add("อุตรดิตถ์");
        arrProvinces.add("อุบลราชธานี");
        arrProvinces.add("อำนาจเจริญ");

        for (int i = 0 ; i < arrProvinces.size() ; i++) {
            if (getWord().equals(arrProvinces.get(i)))
                return i;
        }
        return 0;
    }
}
