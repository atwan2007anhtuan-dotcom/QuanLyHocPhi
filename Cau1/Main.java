
import java.util.Scanner;

class SinhVien {
    private String maSV;
    private String hoTen;
    private String ngaySinh;

    // Ham chuan hoa ho ten
    public String chuanHoaTen(String ten) {
        ten = ten.trim().toLowerCase();

        String[] words = ten.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            String newWord = word.substring(0, 1).toUpperCase()
                    + word.substring(1);

            if (result.length() > 0) {
                result.append(" ");
            }

            result.append(newWord);
        }

        return result.toString();
    }

    // Ham chuan hoa ngay sinh
    public String chuanHoaNgaySinh(String ngaySinh) {
        String[] parts = ngaySinh.split("/");

        int ngay = Integer.parseInt(parts[0]);
        int thang = Integer.parseInt(parts[1]);
        int nam = Integer.parseInt(parts[2]);

        return String.format("%02d/%02d/%04d", ngay, thang, nam);
    }

    // Constructor
    public SinhVien(String maSV, String hoTen, String ngaySinh) {
        this.maSV = maSV.trim();
        this.hoTen = chuanHoaTen(hoTen);
        this.ngaySinh = chuanHoaNgaySinh(ngaySinh);
    }

    // Ham in thong tin
    public void hienThi() {
        System.out.println(maSV + " " + hoTen + " " + ngaySinh);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        SinhVien[] danhSach = new SinhVien[n];

        for (int i = 0; i < n; i++) {
            String maSV = sc.nextLine();
            String hoTen = sc.nextLine();
            String ngaySinh = sc.nextLine();

            danhSach[i] = new SinhVien(maSV, hoTen, ngaySinh);
        }

        for (SinhVien sv : danhSach) {
            sv.hienThi();
        }

        sc.close();
    }
}
