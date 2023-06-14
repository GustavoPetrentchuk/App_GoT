import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projeto3.data.Character

@Composable
fun CharacterDetailsView(character: Character) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "ID: ${character.id}")
        Text(text = "Full Name: ${character.fullName}")
        Text(text = "Family: ${character.family}")
        Text(text = "Title: ${character.title}")
        Text(text = "Image URL: ${character.imageUrl}")
    }
}