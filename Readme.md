# Yammr Backend

An anonymous social platform for posting text, pictures, and "vine-style" videos, exclusively for college students. This repository houses the Spring Boot back-end that serves the front-end. 

### Flag Post

Users may flag a post for review if they feel the post does not meet Yammr's community guidelines.  Flagged posts may be reviewed by moderators or deleted entirely.


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

