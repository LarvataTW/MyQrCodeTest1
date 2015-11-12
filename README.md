 雲端Android App 與  智慧商圈雲端平台 API 串接實作範例訓練
 =======================
 
 使用 Android studio 實作Android App與智慧商圈雲端平台 API 串接實作範例訓練。 
 本範例會讓使用者模擬店家透過API，連線主機核銷優惠券。
 
 
 API
 =========== 
 
 http://workshop.livingfun.net/boss/:uuid/result?user_email=:email&user_token=:user_token 
 
 參數說明：
 
 :uuid   : 掃描出來的qrcode內容
 :email  : 使用者的email 
 :user_toekn : 使用者的token
 
 回傳值：
 
 {"success":false,"message":"找不到此張優惠券或是已被兌換"}
{"success":true,"message":"兌換成功"}
