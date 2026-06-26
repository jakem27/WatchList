# WatchList
CRUD web application for making and sharing movie watch lists. Made for Dev10 capstone project

### User Stories
- User opens website, directed to home page with a welcome message.
- Must login/signup to use the site.
- Once logged in, user can begin creating their WatchList by creating folders (for organization by genre, actor, director, etc) and searching for movies to add to those folders.
- The entire WatchList is private by default, but users can mark the whole thing or individual folders as public to share with their friends.
- If a folder is marked as public, all subfolders are also public.
- User can add the streaming services that they have to their profile. All movies in their WatchList that are available on one of these services will be indicated as so.
- To add a friend, user will look them up by username and send a request. If that user accepts, then they can both view each others public folders.
- When viewing a friends WatchList, user can easily add a movie they see to their own WatchList
- User can mark a movie as watched, which updates their stats
- User can view stats about the movies they've watched such as movies per weeek, total minutes, breakdowns by genre


### To-Dos
- make username case sensitive for login/signup
- automatic logout for expired tokens
- folder stack not synced with database
- standardize sql commands for readability
- complete backend test suit
- make admin accounts for editing movie availability
- use spring ai to suggest movies to users
- improve frontend styling