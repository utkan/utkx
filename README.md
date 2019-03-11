# Android Test

The test took more then 6 hours and unfortunately tests are missing.

## Flow

* First user gets first 6 Character from Marvel api without "Go to categories" button
* Non first time user gets first 6 Character from Marvel api with "Go to categories" button
* Item selection from the first screen opens the categories for the first time user
* Item selection from the first screen or "Go to categories" button, opens the categories for the non first time user
* Every item click tracked to db on categories screen and this view count used for span count and ordering the list

### Architecture

```
UI -> Presentation -> Domain -> Data -> Remote
```
* Logic separated in to _Presentation_ and _Domain_ module
* _Data_ modules how to fetch data or store data
* _Data_ module saves tracking data and merges with fetched data from _Remote_ module

## What is missing
* Unit tests
* Things TODO:
* Span count logic should be handled by separate class to test easily and to increase readability

**Thank you for your _time_**
