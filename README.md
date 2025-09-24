# 🌐 SocialMedia-API

This is a **Social Media Backend API** developed using **Spring Boot**. It supports both user and admin operations via a single API and provides functionality such as authentication, post creation, following/unfollowing users, bookmarking posts, commenting, liking, reporting content, and more.

---

## 🚀 Features

### 👤 Authentication
- `loginUser` — User login with JWT token.
- `signUpUser` — New user registration with encrypted password.

### 🛠 Admin Operations
- `getPostReport`, `getCommentReport`, `getUsersReport` — View reported posts, comments, and users.
- `checkPostReport` — Approve or reject reported posts.

### 📌 Bookmarks
- `getMarkedPosts` — Show marked posts.
- `addBookmarks` — Bookmark a post.
- `deleteBookmarks` — Remove a bookmarked post.

### 💬 Comments
- `createNewComment` — Add comment to a post.
- `deleteComment` — Delete user’s comment.

### 🔍 Explore
- `getExplorePosts` — View trending or suggested posts.
- `getUsers` — Discover new users.

### 🤝 Follow System
- `followUser` — Follow another user.
- `unfollowUser` — Unfollow a user.

### ❤️ Likes
- `likePost` — Like a post.
- `unlikePost` — Unlike a post.

### 📸 Posts
- `getUsersPosts` — Get all posts from a user.
- `createNewPost` — Create a new post.
- `editPost` — Edit an existing post.
- `deletePost` — Delete a post.

### 🚨 Reports
- `reportPost`, `reportComment`, `reportUser` — Report content or users for review.

### 📖 Stories
- `showMyStory` — View user's own stories.
- `createNewStory` — Create a new story.
- `deleteStory` — Delete a story.

### 🧾 User Management
- `getAccountDetails` — View profile and account details.
- `getFollowingAccounts`, `getFollowersAccounts` — View following/followers list.
- `getLikedPosts` — View liked posts.
- `deleteFollower` — Remove a follower.
- `editAccountDetails` — Edit profile info.
- `editAccountPassword` — Change account password.
- `accountDelete` — Permanently delete account.

---

## 🛠 Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Stream API**
- **PostgreSQL** (Relational DB)
- **MongoDB** (NoSQL DB for fast-access data like stories)
- **Pagination** (Efficient browsing of posts and user lists)
- **Swagger UI** (API documentation and testing)
- **JUnit & Mockito** (Unit testing and mocking)

---

