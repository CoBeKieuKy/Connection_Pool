Chương trình tạo chat đơn giản gồm 4 file: Client, ChatServer, ClientThread, ServerThread
  Trong đó, Client và ChatServer sử dụng để tạo người dùng và server quản lí người dùng. Mỗi khi chạy file Client sẽ tạo ra 1 người dùng mới 
connect tới server trên và được nhận số hiệu cổng tương ứng riêng biệt với từng người dùng.
  Các file ClientThread và ServerThread đảm nhiệm việc nhận và hiển thị tin nhắn.
  Mỗi khi người dùng gõ exit thì tự động sẽ ngắt kết nối với server. Số lượng người connect trong 1 lần có giới hạn nên nếu quá sẽ bị không connect được.
