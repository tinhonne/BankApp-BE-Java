# ỨNG DỤNG QUẢN LÝ TÀI KHOẢN

## 1. Data Model

### 1.1. Entity Customer (Khách hàng)

| STT | Tên Trường       | Kiểu dữ liệu      | Validate                                      | Ghi chú |
|-----|------------------|-------------------|-----------------------------------------------|---------|
| 1   | id               | Long              | Not null, Primary Key                         | - |
| 2   | name             | String            | Not null, độ dài < 100 ký tự                  | Tên chủ tài khoản |
| 3   | birthday         | LocalDateTime     | Not null                                      | Ngày sinh |
| 4   | address          | String            | Not null                                      | Địa chỉ thường trú |
| 5   | identityNo       | String            | Not null, đúng 10 ký tự số, Unique            | Số CMT / Căn cước |
| 6   | mobile           | String            | Có thể null, 9-10 số                          | Số điện thoại |
| 7   | customerType     | String (ENUM)     | Not null: `INDIVIDUAL`, `CORPORATE`           | INDIVIDUAL: Cá nhân<br>CORPORATE: Doanh nghiệp |
| 8   | status           | Integer           | Not null                                      | 0: Hết hiệu lực<br>1: Hiệu lực |
| 9   | createDateTime   | LocalDateTime     | Not null                                      | Thời gian tạo |
| 10  | updateDateTime   | LocalDateTime     | Not null                                      | Thời gian cập nhật gần nhất |

### 1.2. Entity Account (Tài khoản)

| STT | Tên Trường        | Kiểu dữ liệu    | Validate                                           | Ghi chú |
|-----|-------------------|-----------------|----------------------------------------------------|---------|
| 1   | id                | Long            | Not null, Primary Key                              | Tự sinh |
| 2   | accountNumber     | String          | Đúng 13 ký tự số, không space, Unique, Not null    | Số tài khoản |
| 3   | customerId        | Long            | Not null, FK → Customer                            | - |
| 4   | balance           | Double          | Not null, ≥ 0                                      | Số dư |
| 5   | status            | Integer         | Not null                                           | 0: Hết HL<br>1: Hiệu lực<br>2: Tạm khóa |
| 6   | createDateTime    | LocalDateTime   | Not null                                           | Thời gian tạo |
| 7   | updateDateTime    | LocalDateTime   | Not null                                           | Thời gian cập nhật |

### 1.3. Entity Transaction (Giao dịch)

| STT | Tên Trường        | Kiểu dữ liệu    | Validate          | Ghi chú |
|-----|-------------------|-----------------|-------------------|---------|
| 1   | id                | Long            | PK                | - |
| 2   | transactionDate   | LocalDateTime   | Not null          | Thời gian GD |
| 3   | fromAccount       | Long            | FK → Account      | TK gửi |
| 4   | toAccount         | Long            | FK → Account      | TK nhận |
| 5   | amount            | Double          | > 0               | Số tiền |
| 6   | status            | Integer         | -                 | 0: Thành công<br>1-4: Các lỗi |
| 7   | content           | String          | -                 | Nội dung |
| 8   | errorReason       | String          | -                 | Lý do lỗi |

### 1.4. ErrorMessage

| Trường   | Kiểu     | Ghi chú |
|----------|----------|---------|
| code     | String   | 6 ký tự (CUSxxx, ACCxxx, TXNxxx) |
| message  | String   | ≤ 250 ký tự |

---

## 2. Danh sách API

### 2.1. Customer APIs

- `GET /customers` — Danh sách khách hàng (phân trang khuyến khích)
- `GET /customers/{id}` — Chi tiết theo ID
- `GET /customers/search` — Tìm theo tên, sdt, cmt, status, type
- `POST /customers` — Thêm mới
- `PUT /customers/{id}` — Cập nhật

### 2.2. Account APIs

- `GET /accounts` — Danh sách tất cả
- `GET /customers/{customerId}/accounts/active` — TK hiệu lực
- `GET /customers/{customerId}/accounts/inactive` — TK hết hiệu lực
- `GET /customers/{customerId}/accounts` — Tất cả TK của KH
- `GET /accounts/{id}` — Theo ID
- `GET /accounts/number/{accountNumber}` — Theo số TK
- `POST /accounts` — Tạo mới (trạng thái Chờ Phê Duyệt)
- `PUT /accounts/{id}/approve` — Phê duyệt (role MANAGER)
- `PUT /accounts/{id}/status` — Cập nhật trạng thái (Hết HL / Tạm khóa)

### 2.3. Transaction APIs

- `POST /transactions/transfer` — Chuyển tiền
- `GET /accounts/{accountNumber}/transactions` — Lịch sử GD theo khoảng thời gian

### 2.4. Report APIs

- `GET /reports/transactions` — Chi tiết GD trong khoảng thời gian (max 60 ngày)
- `GET /reports/summary` — Thống kê theo ngày, loại KH, trạng thái

### 2.5. Export (Khuyến khích)

- Scheduler chạy lúc 00:00:01 hàng ngày
- Export GD ngày hôm trước ra file: `transaction_yyyyMMdd.csv`

---

## 3. Yêu cầu chung

- Thiết kế API Path, Method, Status Code thống nhất
- Xử lý lỗi thống nhất (Error Code + Message)
- Viết Unit Test > 75% coverage
- Ghi log các bước quan trọng
- Khuyến khích dùng **H2 Embedded Database**

---

