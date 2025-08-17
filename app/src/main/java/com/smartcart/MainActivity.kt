package com.smartcart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smartcart.data.database.DatabaseSeeder
import com.smartcart.data.database.SmartCartDatabase
import com.smartcart.data.repository.ShoppingListWithItemCount
import com.smartcart.data.repository.SmartCartRepository
import com.smartcart.viewmodel.SmartCartViewModel
import com.smartcart.ui.theme.SmartCartTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            
            SmartCartTheme(darkTheme = isDarkTheme) {
                SmartCartApp(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartCartApp(
    isDarkTheme: Boolean = false,
    onThemeToggle: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: SmartCartViewModel = viewModel()
    
    // Seed database with sample data on first run
    LaunchedEffect(Unit) {
        val database = SmartCartDatabase.getDatabase(context)
        val repository = SmartCartRepository(
            database.shoppingListDao(),
            database.shoppingItemDao()
        )
        val seeder = DatabaseSeeder(repository)
        
        // Seed database in background
        CoroutineScope(Dispatchers.IO).launch {
            try {
                seeder.seedDatabase()
            } catch (e: Exception) {
                // Database might already be seeded
            }
        }
    }
    
    // Navigation state
    var currentScreen by remember { mutableStateOf("main") }
    var selectedList by remember { mutableStateOf<ShoppingListWithItemCount?>(null) }
    
    // Observe shopping lists from database
    val shoppingLists by viewModel.allLists.collectAsState(initial = emptyList())
    
    when (currentScreen) {
        "main" -> MainScreen(
            shoppingLists = shoppingLists,
            isDarkTheme = isDarkTheme,
            onThemeToggle = onThemeToggle,
            onListClick = { list ->
                selectedList = list
                currentScreen = "detail"
            },
            onAddList = {
                viewModel.insertList("New List")
            },
            onEditList = { listId, newName ->
                val listToEdit = shoppingLists.find { it.id == listId }
                listToEdit?.let { viewModel.updateList(it, newName) }
            },
            onDeleteList = { listId ->
                viewModel.deleteList(listId)
            }
        )
        "detail" -> selectedList?.let { list ->
            ListDetailScreen(
                listId = list.id,
                listName = list.name,
                onBackClick = {
                    currentScreen = "main"
                    selectedList = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    shoppingLists: List<ShoppingListWithItemCount>,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onListClick: (ShoppingListWithItemCount) -> Unit,
    onAddList: () -> Unit,
    onEditList: (Int, String) -> Unit,
    onDeleteList: (Int) -> Unit
) {
    var editingList by remember { mutableStateOf<ShoppingListWithItemCount?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("SmartCart")
                    }
                },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddList
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add List")
            }
        }
    ) { paddingValues ->
        if (shoppingLists.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No shopping lists yet",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "Tap + to create your first list",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            // Lists content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shoppingLists) { list ->
                    ShoppingListCard(
                        shoppingList = list,
                        onListClick = { 
                            onListClick(list)
                        },
                        onEditClick = { listId, currentName ->
                            editingList = list
                            showEditDialog = true
                        },
                        onDeleteClick = {
                            onDeleteList(list.id)
                        }
                    )
                }
            }
        }
    }
    
    // Edit List Dialog
    if (showEditDialog && editingList != null) {
        EditListDialog(
            listName = editingList!!.name,
            onDismiss = { 
                showEditDialog = false
                editingList = null
            },
            onSave = { newName ->
                onEditList(editingList!!.id, newName)
                showEditDialog = false
                editingList = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListCard(
    shoppingList: ShoppingListWithItemCount,
    onListClick: () -> Unit,
    onEditClick: (Int, String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onListClick,
        colors = CardDefaults.cardColors(
            containerColor = if (shoppingList.isCompleted) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = shoppingList.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (shoppingList.isCompleted) {
                        MaterialTheme.colorScheme.outline
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.weight(1f)
                )
                
                IconButton(
                    onClick = { onEditClick(shoppingList.id, shoppingList.name) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit List",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                
                TextButton(onClick = onDeleteClick) {
                    Text("Delete")
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "${shoppingList.completedItems}/${shoppingList.totalItems} items",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
            
            if (shoppingList.isCompleted) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "✓ Completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditListDialog(
    listName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var editedName by remember { mutableStateOf(listName) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit List Name") },
        text = {
            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it },
                label = { Text("List Name") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (editedName.isNotBlank()) {
                        onSave(editedName)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SmartCartPreview() {
    SmartCartTheme {
        SmartCartApp()
    }
}