package com.mizo0203.komeiji.repo;

import com.mizo0203.komeiji.domain.difine.TwitterUser;
import twitter4j.*;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TwitterClient {

  private static final Logger LOG = Logger.getLogger(TwitterClient.class.getName());
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_WEBHOOKS_ENV_NAME_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/env-beta/webhooks.json?url=https%3A%2F%2Fapi-project-93144643231.appspot.com%2Ftwitter_hook";
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_WEBHOOKS_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/webhooks.json";
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_SUBSCRIPTIONS_ENV_NAME_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/env-beta/subscriptions.json";
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_COUNT_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/count.json";
  private static final String TWITTER_API_ACCOUNT_ACTIVITY_LIST_SUBSCRIPTIONS_URL_STR =
      "https://api.twitter.com/1.1/account_activity/all/env-beta/subscriptions/list.json";
  private final Twitter mTwitter;
  private final Twitter4JUtil mTwitter4JUtil;

  public TwitterClient() {
    mTwitter4JUtil = new Twitter4JUtil();
    mTwitter = mTwitter4JUtil.getUserAuthentication().getTwitter();
  }

  public void registersWebhookURL() {
    try {
      Method method = mTwitter.getClass().getDeclaredMethod("post", String.class);
      method.setAccessible(true);
      HttpResponse ret =
          (HttpResponse)
              method.invoke(mTwitter, TWITTER_API_ACCOUNT_ACTIVITY_WEBHOOKS_ENV_NAME_URL_STR);
      LOG.log(Level.INFO, "registersWebhookURL ret: " + ret);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      LOG.log(Level.SEVERE, "registersWebhookURL", e);
    }
  }

  /**
   * Returns all URLs and their statuses for the given app for all events. Currently, only one
   * webhook URL can be registered to an application. We mark a URL as invalid if it fails the daily
   * validation check. In order to re-enable the URL, call the update endpoint.
   *
   * <p>指定されたすべてのイベントのURLとそのステータスを返します。 現在、Webhook URLはアプリケーションに1つしか登録できません。
   * 毎日の妥当性チェックに失敗した場合は、URLを無効とマークします。 URLを再度有効にするには、更新エンドポイントを呼び出します。
   */
  public void returnsAllUrls() {
    try {
      HttpResponse ret =
          mTwitter4JUtil.getUserAuthentication().get(TWITTER_API_ACCOUNT_ACTIVITY_WEBHOOKS_URL_STR);
      LOG.log(Level.INFO, "returnsAllUrls ret.toString(): " + ret.toString());
      LOG.log(Level.INFO, "returnsAllUrls ret.asString(): " + ret.asString());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  public void subscriptions() {
    try {
      Method method = mTwitter.getClass().getDeclaredMethod("post", String.class);
      method.setAccessible(true);
      HttpResponse ret =
          (HttpResponse)
              method.invoke(mTwitter, TWITTER_API_ACCOUNT_ACTIVITY_SUBSCRIPTIONS_ENV_NAME_URL_STR);
      LOG.log(Level.INFO, "subscriptions ret: " + ret);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      LOG.log(Level.SEVERE, "subscriptions", e);
    }
  }

  /**
   * Returns the count of subscriptions that are currently active on your account for all
   * activities. Note that the /count endpoint requires App-only Oauth, so that you should make
   * requests using a bearer token instead of app-user auth.
   *
   * <p>すべてのアクティビティに対してアカウントで現在アクティブなサブスクリプションの数を返します。 / countエンドポイントにはApp-only Oauthが必要なので、app-user
   * authの代わりにベアラトークンを使用してリクエストを行う必要があることに注意してください。
   */
  public void countSubscriptions() {
    try {
      HttpResponse ret =
          mTwitter4JUtil
              .getApplicationOnlyAuthentication()
              .get(TWITTER_API_ACCOUNT_ACTIVITY_COUNT_URL_STR);
      LOG.log(Level.INFO, "countSubscriptions ret.toString(): " + ret.toString());
      LOG.log(Level.INFO, "countSubscriptions ret.asString(): " + ret.asString());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  /**
   * Provides a way to determine if a webhook configuration is subscribed to the provided user’s All
   * events. If the provided user context has an active subscription with provided app, returns 204
   * OK. If the response code is not 204, then the user does not have an active subscription. See
   * HTTP Response code and error messages below for details.
   *
   * <p>Webhook設定が、提供されたユーザのすべてのイベントに登録されているかどうかを判断する方法を提供します。
   * 提供されたユーザーコンテキストに、提供されたアプリケーションによるアクティブなサブスクリプションがある場合は、204 OKを返します。
   * 応答コードが204でない場合、ユーザーにはアクティブなサブスクリプションがありません。 詳細については、下記の「HTTPレスポンスコードとエラーメッセージ」を参照してください。
   */
  public void isSubscribed() {
    try {
      HttpResponse ret =
          mTwitter4JUtil
              .getUserAuthentication()
              .get(TWITTER_API_ACCOUNT_ACTIVITY_SUBSCRIPTIONS_ENV_NAME_URL_STR);
      LOG.log(Level.INFO, "isSubscribed ret.toString(): " + ret.toString());
      LOG.log(Level.INFO, "isSubscribed ret.asString(): " + ret.asString());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a list of the current All Activity type subscriptions. Note that the /list endpoint
   * requires App-only Oauth, so requests should be made using a bearer token instead of app-user
   * auth.
   *
   * <p>現在のAll Activity型サブスクリプションのリストを返します。 / listエンドポイントではApp-only Oauthが必要なので、app-user
   * authの代わりにベアラトークンを使用してリクエストする必要があります。
   */
  public void listCurrentAllActivityTypeSubscriptions() {
    try {
      HttpResponse ret =
          mTwitter4JUtil
              .getApplicationOnlyAuthentication()
              .get(TWITTER_API_ACCOUNT_ACTIVITY_LIST_SUBSCRIPTIONS_URL_STR);
      LOG.log(
          Level.INFO, "listCurrentAllActivityTypeSubscriptions ret.toString(): " + ret.toString());
      LOG.log(
          Level.INFO, "listCurrentAllActivityTypeSubscriptions ret.asString(): " + ret.asString());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deactivates subscription(s) for the provided user context and app for all activities. After
   * deactivation, all All events for the requesting user will no longer be sent to the webhook URL.
   *
   * <p>提供されたユーザーコンテキストのサブスクリプションとすべてのアクティビティのアプリケーションを非アクティブ化します。
   * 非アクティブ化後、要求元ユーザのすべてのすべてのイベントはWebHook URLに送信されなくなります。
   */
  public void deactivatesSubscriptions() {
    try {
      HttpResponse ret =
          mTwitter4JUtil
              .getUserAuthentication()
              .delete(TWITTER_API_ACCOUNT_ACTIVITY_SUBSCRIPTIONS_ENV_NAME_URL_STR);
      LOG.log(Level.INFO, "deactivatesSubscriptions ret.toString(): " + ret.toString());
      LOG.log(Level.INFO, "deactivatesSubscriptions ret.asString(): " + ret.asString());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the authenticating user’s current status, also known as Tweeting. For each update
   * attempt, the update text is compared with the authenticating user’s recent Tweets. Any attempt
   * that would result in duplication will be blocked, resulting in a 403 error. A user cannot
   * submit the same status twice in a row. While not rate limited by the API, a user is limited in
   * the number of Tweets they can create at a time. If the number of updates posted by the user
   * reaches the current allowed limit this method will return an HTTP 403 error.
   *
   * @see <a
   *     href="https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/post-statuses-update">POST
   *     statuses/update — Twitter Developers</a>
   */
  public Status updateStatus(TwitterUser twitterUser, String statusString) {
    Twitter twitter = getTwitter(twitterUser);
    try {
      return twitter.updateStatus(statusString);
    } catch (TwitterException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Nonnull
  private Twitter getTwitter(TwitterUser twitterUser) {
    switch (twitterUser) {
      case MIZO0203:
        return mTwitter4JUtil.getUserAuthentication().getTwitter();
      case REDMIZO:
        return mTwitter4JUtil.getRedmizoUserAuthentication().getTwitter();
      default:
        throw new IllegalArgumentException("twitterUser: " + twitterUser);
    }
  }
}
