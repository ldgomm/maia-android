package com.premierdarkcoffee.sales.maia.root.feature.product.presentation.view.edit

/*@Composable
fun AddEditInformationView(onAddInformationResultStateButtonClick: (informationResultState: InformationResultState) -> Unit) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(contract = PickVisualMedia()) {
        selectedImageUri = it
    }

    Column(modifier = Modifier.padding(16.dp)) {
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = six)
            .padding(horizontal = eleven),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(four)) {
            selectedImageUri?.let { image ->
                AsyncImage(model = image, contentDescription = "", modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    }, contentScale = ContentScale.Crop)
            } ?: run {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp)
                    .clickable {
                        photoPickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    }, contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = six)
            .padding(horizontal = eleven),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(four)) {

            TextField(value = title,
                      onValueChange = { title = it },
                      label = { Text(text = "Title") },
                      modifier = Modifier.fillMaxWidth())

        }
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = six)
            .padding(horizontal = eleven),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(four)) {
            TextField(value = subtitle,
                      onValueChange = { subtitle = it },
                      label = { Text(text = "Subtitle") },
                      modifier = Modifier.fillMaxWidth())
        }
        ElevatedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = six)
            .padding(horizontal = eleven),
                     shape = MaterialTheme.shapes.medium,
                     elevation = CardDefaults.elevatedCardElevation(four)) {
            TextField(value = description,
                      onValueChange = { description = it },
                      label = { Text(text = "Description") },
                      modifier = Modifier.fillMaxWidth())
        }

        Button(onClick = {
            val informationResultState = InformationResultState(id = UUID.randomUUID().toString(),
                                                                title = title,
                                                                subtitle = subtitle,
                                                                description = description,
                                                                image = selectedImageUri,
                                                                path = "",
                                                                belongs = true,
                                                                place = 0,
                                                                isCreated = true,
                                                                isDeleted = false)
            onAddInformationResultStateButtonClick(informationResultState)
        }, modifier = Modifier.padding(16.dp)) {
            Text("Submit")
        }
    }
}*/
