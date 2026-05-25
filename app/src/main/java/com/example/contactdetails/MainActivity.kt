package com.example.contactdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactdetails.ui.theme.ContactDetailsTheme

data class Contact(
    val name: String,
    val surname: String? = null,
    val familyName: String,
    val imageRes: Int? = null,
    val isFavorite: Boolean = false,
    val phone: String,
    val address: String,
    val email: String? = null,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactDetailsTheme {
                ContactDetailsApp(
                    contact = Contact(
                        name = "Евгений",
                        surname = "Андреевич",
                        familyName = "Лукашин",
                        isFavorite = true,
                        phone = "+7 495 495 95 95",
                        address = "г. Москва, 3-я улица\nСтроителей, д. 25, кв. 12",
                        email = "ELukashin@practicum.ru"
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsApp(contact: Contact) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        ContactDetails(
            contact = contact,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContactDetails(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        ContactPhoto(contact = contact)
        Spacer(modifier = Modifier.height(24.dp))
        ContactName(contact = contact)
        Spacer(modifier = Modifier.height(68.dp))
        InfoRow(
            title = stringResource(R.string.phone),
            value = contact.phone
        )
        Spacer(modifier = Modifier.height(28.dp))
        InfoRow(
            title = stringResource(R.string.address),
            value = contact.address
        )
        contact.email?.let { email ->
            Spacer(modifier = Modifier.height(28.dp))
            InfoRow(
                title = stringResource(R.string.email),
                value = email
            )
        }
    }
}

@Composable
private fun ContactPhoto(contact: Contact) {
    if (contact.imageRes != null) {
        Image(
            painter = painterResource(contact.imageRes),
            contentDescription = null,
            modifier = Modifier
                .width(120.dp)
                .height(78.dp),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier.size(72.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.circle),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape),
                tint = Color(0xFFD1D1D1)
            )
            Text(
                text = contact.initials(),
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ContactName(contact: Contact) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (contact.isFavorite) {
            Spacer(modifier = Modifier.width(36.dp))
        }
        Text(
            text = contact.displayName(),
            modifier = Modifier.width(250.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold
        )
        if (contact.isFavorite) {
            Image(
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(28.dp)
            )
        }
    }
}

@Composable
private fun InfoRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.width(300.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$title:",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            color = Color(0xFF202124),
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            modifier = Modifier.weight(1.35f),
            color = Color(0xFF202124),
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
    }
}

private fun Contact.displayName(): String {
    return if (surname.isNullOrBlank()) {
        "$name\n$familyName"
    } else {
        "$name $surname\n$familyName"
    }
}

private fun Contact.initials(): String {
    return "${name.take(1)}${familyName.take(1)}".uppercase()
}

@Preview(
    name = "ProfileWithoutPhotoPreview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ProfileWithoutPhotoPreview() {
    ContactDetailsTheme {
        ContactDetailsApp(
            contact = Contact(
                name = "Евгений",
                surname = "Андреевич",
                familyName = "Лукашин",
                isFavorite = true,
                phone = "+7 495 495 95 95",
                address = "г. Москва, 3-я улица\nСтроителей, д. 25, кв. 12",
                email = "ELukashin@practicum.ru"
            )
        )
    }
}

@Preview(
    name = "ProfileWithPhotoPreview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ProfileWithPhotoPreview() {
    ContactDetailsTheme {
        ContactDetailsApp(
            contact = Contact(
                name = "Василий",
                familyName = "Кузякин",
                imageRes = R.drawable.contact_photo,
                phone = "---",
                address = "Ивановская область, дер.\nКрутово, д. 4"
            )
        )
    }
}
