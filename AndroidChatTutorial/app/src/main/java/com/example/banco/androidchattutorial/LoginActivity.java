package com.example.banco.androidchattutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

public class LoginActivity extends AppCompatActivity {
    // Sample APP ID provided by SendBird
    // PersonalChat CC69A595-8036-4701-A9C4-D76B296D592F
    // Tutorial 9DA1B1F4-0BE6-4DA8-82C5-2E81DAB56F23
    private static final String APP_ID = "CC69A595-8036-4701-A9C4-D76B296D592F";
    private static final String TAG = "MainActivity";
    private Button mConnectButton;
    private TextInputEditText mUserIdEditText, mUserNicknameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mConnectButton = (Button) findViewById(R.id.button_login);
        mUserIdEditText = (TextInputEditText) findViewById(R.id.edit_text_login_user_id);
        mUserNicknameEditText = (TextInputEditText) findViewById(R.id.edit_text_login_user_nickname);

        // Initialize the SendBird SDK.
        SendBird.init(APP_ID, this.getApplicationContext());
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        SendBird.registerPushTokenForCurrentUser(token, new SendBird.RegisterPushTokenWithStatusHandler() {
                            @Override
                            public void onRegistered(SendBird.PushTokenRegistrationStatus ptrs, SendBirdException e) {
                                if (e != null) {
                                    return;
                                }

                                if (ptrs == SendBird.PushTokenRegistrationStatus.PENDING) {
                                    // Try registering the token after a connection has been successfully established.
                                }
                            }
                        });
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mUserIdEditText.getText().toString();
                // Remove all spaces from userID
                userId = userId.replaceAll("\\s", "");

                String userNickname = mUserNicknameEditText.getText().toString();

                connectToSendBird(userId, userNickname);
            }
        });
    }

    /**
     * Attempts to connect a user to SendBird.
     * @param userId The unique ID of the user.
     * @param userNickname The user's nickname, which will be displayed in chats.
     */
    private void connectToSendBird(final String userId, final String userNickname) {
        mConnectButton.setEnabled(false);

        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {

                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    mConnectButton.setEnabled(true);
                    return;
                }
                if (FirebaseInstanceId.getInstance().getToken() == null) return;

                SendBird.registerPushTokenForCurrentUser(FirebaseInstanceId.getInstance().getToken(),
                        new SendBird.RegisterPushTokenWithStatusHandler() {
                            @Override
                            public void onRegistered(SendBird.PushTokenRegistrationStatus status, SendBirdException e) {
                                if (e != null) {    // Error.
                                    return;
                                }
                                setPushNotification(true,SendBird.getPendingPushToken());
                            }
                        }
                );
                // Update the user's nickname
                updateCurrentUserInfo(userNickname);

                Intent intent = new Intent(LoginActivity.this, ChatActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setPushNotification(boolean enable, String gcmRegToken) {
        if (enable) {
            SendBird.registerPushTokenForCurrentUser(gcmRegToken,
                    new SendBird.RegisterPushTokenWithStatusHandler() {
                        @Override
                        public void onRegistered(SendBird.PushTokenRegistrationStatus status, SendBirdException e) {
                            if (e != null) {    // Error.
                                return;
                            }
                        }
                    }
            );
        }
        else {
            // If you want to unregister the current device only, invoke this method.
            SendBird.unregisterPushTokenForCurrentUser(gcmRegToken,
                    new SendBird.UnregisterPushTokenHandler() {
                        @Override
                        public void onUnregistered(SendBirdException e) {
                            if (e != null) {    // Error.
                                return;
                            }
                        }
                    }
            );

            // If you want to unregister the all devices of the user, invoke this method.
            SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
                @Override
                public void onUnregistered(SendBirdException e) {
                    if (e != null) {    // Error.
                        return;
                    }
                }
            });
        }
    }
    /**
     * Updates the user's nickname.
     * @param userNickname The new nickname of the user.
     */
    private void updateCurrentUserInfo(String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

            }
        });
    }

}