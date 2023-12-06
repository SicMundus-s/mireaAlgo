package task3;

import rx.Observable;

public record UserFriend(int userId, int friendId) {


    public static Observable<UserFriend> getFriends(int userId) {
        UserFriend[] userFriends = generateRandomUserFriends(new Integer[]{userId});
        return Observable.from(userFriends);
    }

    public static UserFriend[] generateRandomUserFriends(Integer[] userIds) {
        UserFriend[] userFriends = new UserFriend[userIds.length * 2];
        int index = 0;
        for (int userId : userIds) {
            userFriends[index++] = new UserFriend(userId, (int) (Math.random() * 10));
            userFriends[index++] = new UserFriend((int) (Math.random() * 10), userId);
        }
        return userFriends;
    }
}
