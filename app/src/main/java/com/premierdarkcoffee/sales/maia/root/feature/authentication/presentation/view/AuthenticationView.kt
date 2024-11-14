package com.premierdarkcoffee.sales.maia.root.feature.authentication.presentation.view

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.premierdarkcoffee.sales.maia.R.drawable.google_logo
import com.premierdarkcoffee.sales.maia.root.util.constant.Constant.clientId
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView(oneTapState: OneTapSignInState,
                       signedInState: Boolean,
                       onSignInWithGoogleButtonClicked: (Boolean) -> Unit,
                       onTokenIdReceived: (String) -> Unit,
                       onDialogDismissed: (String) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .nestedScroll(scrollBehavior.nestedScrollConnection)
        .statusBarsPadding()
        .navigationBarsPadding(), topBar = {
        MediumTopAppBar(title = {
            Text("Maia", maxLines = 1, overflow = TextOverflow.Clip)
        }, scrollBehavior = scrollBehavior)
    }) {
        Column(Modifier.padding(it),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally) {
            Column(Modifier.weight(1f)) {}
            Column(modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally) {
                Image(modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(120.dp),
                      painter = painterResource(id = google_logo),
                      contentDescription = "Google Logo")
                Text(text = "Title", fontWeight = FontWeight.Bold)
                Text(text = "Subtitle", modifier = Modifier.padding(bottom = 40.dp, top = 4.dp), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(30.dp))
                SignInWithGoogleButtonView(signedInState = signedInState,
                                           onSignInWithGoogleButtonClicked = onSignInWithGoogleButtonClicked)
            }
        }
    }

    OneTapSignInWithGoogle(state = oneTapState, clientId = clientId, onTokenIdReceived = { tokenId ->
        Log.d(TAG, "AuthenticationView: token = $tokenId")
        onTokenIdReceived(tokenId)
    }, onDialogDismissed = onDialogDismissed)
}
