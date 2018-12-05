# Yammr Backend

An anonymous social platform for posting text, pictures, and "vine-style" videos, exclusively for college students. This repository houses the Spring Boot back-end that serves the front-end. 

## REST API

The API is accessible via [gibgab.us-east-1.elasticbeanstalk.com](http://gibgab.us-east-1.elasticbeanstalk.com)

### Register

A user may register themselves at [`/register`](http://gibgab.us-east-1.elasticbeanstalk.com/).

On successful registration, the user will be sent a verification email to validate their account.  The user will not be able to use Yammr until they have completed the verification process.

##### Endpoint: `/register`
##### Endpoint type: `POST`
##### Expected body:
``` 
{
    email : <user_email>
    password : <user_password>
}
```
##### Results:
* `Created`: The account was created, and the verification email has been sent
* `500 Internal Server Error`, `message: Could not send email through SES`: The account was created, but there was a problem sending the email to the given email.

### Login

A user may log in at [`/login`](http://gibgab.us-east-1.elasticbeanstalk.com/login).

If the user has verified their email then they will be able to log in with their credentials.  The user must include the returned JWT in every authorized API call.

##### Endpoint: `/login`
##### Endpoint type: `POST`
##### Expected body:
``` 
{
    email : <user_email>
    password : <user_password>
}
```
##### Results (applicable):
* `200 Ok`, `Authorization : "Bearer <jwt_token>"`: the user is logged in and they may use `<jwt_token>` to access authorized endpoints.
* `403 Forbidden`: The user was not logged in.  Either the account does not exist or its email has not been verified.

### Verify

The user will be sent an email upon successful registration.  The email will include a link containing their verification token.  Once they go to this link, the user's account is unblocked and they may access the rest of Yammr.

##### Endpoint: `/verify`
##### Endpoint type: `GET`
##### Expected Parameters:
``` key=<verification key> ```
##### Results:
* Success page: The account has been verified.
* 'Oops' page: The verification token is invalid in some way.

### Delete My Account

A user may delete their account by visiting `/user/delete`.  

***THERE IS NO CONFIRMATION! THE USER WILL BE PERMANENTLY DELETED!***

##### Endpoint: `/user/delete`
##### Endpoint type: `POST, GET`

### Ban a User

Moderators may ban users.  When a user is banned, they are unable to access authorized endpoints until their ban expires.

The user will be unable to successfully log in until their ban expires.  The user must log in successfully after their ban expires to receive a new, valid JWT.  

##### Endpoint: `/moderator/ban_user`
##### Endpoint type: `POST`
##### Expected body:
``` 
{
    emailToBan : <user_email>
    banStart : <ban start time>
    bannedUntil : <ban expiration>
}
```
### Make Post

##### Endpoint: `/post`
##### Endpoint type: `POST`
##### Expected body:
```
{
    text : <text>
}
```
##### Results
* Status `200 <post data>`: Post has been made

### Get Feed

##### Endpoint: `/feed`
##### Endpoint type: `GET`
##### Expected parameters: `none`
##### Results:
```
[
    {
        "postId": <post id>,
        "postTime": <YYYY-MM-DDThh:mm:ss.SSS+0000>,
        "text": <text>,
        "score": <score>,
        "voteType": null,
        "authorName": null,
        "authorPictureUrl": null,
        "imageUrl": null,
        "replyCount": 0,
        "comments": []
    }
]
```

### Flag Post

Users may flag a post for review if they feel the post does not meet Yammr's community guidelines.  Flagged posts may be reviewed by moderators or deleted entirely.

If the post received too many flags then the AutoModerator will delete this post automagically.
##### Endpoint: `/post/flag`
##### Endpoint type: `POST`
##### Expected body:
``` 
{
    postId : <post id>
}
```
##### Results
* Status `200 OK`: Post has been flagged by the user
* Status `400 'Post already flagged by this user'`: Post has already been flagged by this user


## Getting Started

TODO

### Prerequisites

TODO

### Installing

TODO

## Running the tests

TODO

### Coding Style

TODO

## Deployment

TODO

## Built With

This project was bootstrapped with [Spring Initializr](start.spring.io) by IntelliJ IDEA.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us

## Versioning

TODO

## Authors

See the list of [contributors](https://github.com/yammr/yammr-backend/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

