# AnalyticsWebInterface
Customização da classe AnalyticsWebInterface para suportar o event type "ecommerce_purchase" com items (produtos) utilizando Google Firebase Analytics + Google Analytics + WebView.

https://firebase.google.com/docs/analytics/android/webview

Para enviar as informações, devemos seguir o seguinte padrão:

logEvent('ecommerce_purchase', {
    "keys": "values",
    "items": [{
        "item_name": "DonutFridayScentedT-Shirt",
        "quantity": 1,
        "price": 29.99,
        "index": 1,
        "item_id": "sku1234"
    },{
        "item_name": " T-Shirt",
        "quantity": 1,
        "price": 29.99,
        "index": 2,
        "item_id": "sku3334"
    }]
});

Parâmetros possíveis (items): https://firebase.google.com/docs/reference/android/com/google/firebase/analytics/FirebaseAnalytics.Param#INDEX
