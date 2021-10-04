# Xrade API REST Documentation

The main purpose of this document is to describe the xrade api.
The api contains endpoints that lead on resource which allow to 
write resource, read resource and other action on it.

Next we will present the ressources

## Analysis resource

It gives the oopportunity to get market analysis result. the request 
is hande by the url

```/api/v1/analysis```.

Let's discuss the methods handled

### Get 

It takes two queries string paramater :

###### limit 

the maximum number of analysis that we want

###### offset

The number of analysis you xant to scape

The request body is in json format
```javascript
{
	"offset": 0,
    "limit": 8,
},
```

An example of api request is following

```/api/v1/analysis?offset=0&limit=10```.

The output format is an array of which a single row is following
```javascript
{
	"market": "erer",
    "max_loss": 8,
    "available_lot": 20000,
    "price": 1,
    "max_profit": 50,
    "total_lot": 20000,
    "id": 1,
    "published": false,
    "analysis": {
         "market": "erer",
         "start_time": "23",
         "timeframe": "H1",
         "end_time": "45",
         "position": 23,
         "order_type": "BUY"
     }
},
```

## Order resource

Making order ono the market regardless of the analysis provided. the request 
is hande by the url

```/api/v1/order```.

Let's discuss the methods handled

### POST

This input is a json body with content type `application/json`. An exemple of input is 
as following 
```javascript
{
    "position": 1233.77,
    "stop_loss": 5,
    "take_profit": 30,
    "lot": 3,
    "amount": 20,
    "order_type": "BUY",
    "market_analaysis_id": 1,
    "member_id": 23
}
```

## Order Process resource

Handling the process on a market order. Actions are about updating . the request 
is hande by the url

```/api/v1/order/process```.

Let's discuss the methods handled

### POST

It propose to create an order process data.

This input is a json body with content type `application/json`. An exemple of input is 
as following 
```javascript
{
	"last_position": 12334.54,
    "benefit": 12334.54,
    "status": "going",
     "market_order_id": 1,
    "close_date": "",
}
```
As response we have 
```javascript
{
    "id": 3,
	"last_position": 12334.54,
    "benefit": 12334.54,
    "status": "going",
     "market_order_id": 1,
    "close_date": "",
}
```
### PUT 

This input is a json body with content type `application/json`. An exemple of input is as following 
```javascript
{
	"last_position": 12334.54,
    "benefit": 12334.54,
    "status": "going",
    "close_date": "",
     "market_order_id": 1,
    "order_process_id": 1
}
```

The request reponse return the same data as json with fields updated.