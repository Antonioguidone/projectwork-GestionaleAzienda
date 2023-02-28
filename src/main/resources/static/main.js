/**
 * 
 */
/**
 * 
 */
 $(document).ready(function() {
	let editMode = false;
	let editId = -1;
	
	let editPersonaleMode = false;
	let editPersonaleId = -1;
	
	console.log(document.cookie);
	
	$('#credenzialierrate').css('display', 'block');
	
	$('#close-detail').click(function() {
		$('#credenzialierrate').css('display', 'none');
			})
			
		$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris.includes('USER') || ris.includes('ADMIN') || ris.includes('ROOT')) {
					$('#login').text('');
					$('<i class="fas fa-user-alt"> Logout</i>').appendTo($('#login'));
					$('#hme').on('click', '#login', function() {
						$.get('logout', function() {
							$('#login').text('');
							$('<i class="fas fa-user-alt"> Login</i>').appendTo($('#login'));
							$('#container-body').load('forbidden.html');
						});
					})
				}
			})
		})

	$('#servizi').click(function(){
		nascondiElementi();
		$('#container-body').html('');
		$('#container-body').load('servizi.html');
	})
	
	
	
	function nascondiElementi() {
	$('#mostra-aggiungi-azienda').css('display', 'none');
	$('#mostra-aggiungi-personale').css('display', 'none');
	
	$('#tabellaaziende').css('display', 'none');
	$('#tabellapersonale').css('display', 'none');
	
	$('#aggiungiazienda').css('display', 'none');
	$('#aggiungidipendente').css('display', 'none');
	
	$('.dipendentiazienda').css('display', 'none');
	
	$('#listaaziende').html('');
	$('#listapersonale').html('');
	$('#dettaglioazienda').html('');
	
	$('#dettagliopersonale').html('');
	$('#dettaglioazienda').css('display', 'none');
	$('#personaleazienda').css('display', 'none');
	$('#titolo').css('display', 'none');
	
	
	}
	
	nascondiElementi();
	
	
	
	
	
	
	$('#mostra-aziende').click(function() {
		$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				//console.log(ris);
			})
		})
	})
	
	
	
	
	$('#signup').click(function() {
		nascondiElementi();
		$('#container-body').load("signup.html");
		})
		
	$('#container-body').on('click', '#bottoneregistra', function() {
		nascondiElementi();
	})
	
	
	
	$('#login').click(function() {
		nascondiElementi();
		$('#container-body').load("login.html");
		})
	
		
	$('#home').click(function() {
		nascondiElementi();
		$('#container-body').css('display', 'inline');
		
		})
	
	$('#mostra-aziende').click(function() {
		
		nascondiElementi();
		$('#tabellaaziende').css('display', 'block');
		$('#mostra-aggiungi-azienda').css('display', 'inline');
		//$('#container-header').css('display', 'none');
		$("#container-body").load("listaaziende.html", function() {
			setTimeout(function(){ getAziende() }, 200);
		});
	})
	
	function getAziende() {
			$.get('aziende', function(res) {
			for(let i = 0; i < res.length; i++) {
				$(`<tr>
						<td>${res[i].azienda_id}</td>
						<td>${res[i].ragione_sociale}</td>
						<td>
						<button class='dettaglioazienda' data-id='${res[i].azienda_id}'><i class='fas fa-eye'></i></button>
						<button class='eliminaazienda' data-id='${res[i].azienda_id}'><i class='fas fa-trash-alt'></i></button>
						<button class='modificaazienda' data-id='${res[i].azienda_id}'><i class='fas fa-pen'></i></button>
						</td>
					</tr>
					
				`).appendTo($('#listaaziende'));
			}
		})
	}
	
	
	
	
	$('#mostra-personale').click(function() {
		nascondiElementi();
		$('#tabellapersonale').css('display', 'block');
		
		
		
			$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris == "") {
					alert('Devi registrarti per visualizzare il personale')
					$('#tabellapersonale').load("vuoto.html");
				} else $("#container-body").load("listapersonale.html", function() {
					setTimeout(function(){ getPersonale() }, 200);
					$('#mostra-aggiungi-personale').css('display', 'inline');
		});
				
			})
		})
		
		
		
		
	})
	
	function getPersonale() {
		$.get('personale', function(res) {
			for(let i = 0; i < res.length; i++) {
				$(`<tr>
						<td>${res[i].personale_id}</td>
						<td>${res[i].nome}  ${res[i].cognome}</td>
						<td>
						<button class='dettagliopersonale' data-id='${res[i].personale_id}'><i class='fas fa-eye'></i></button>
						<button class='eliminapersonale' data-id='${res[i].personale_id}'><i class='fas fa-trash-alt'></i></button>
						<button class='modificapersonale' data-id='${res[i].personale_id}'><i class='fas fa-pen'></i></i></button>
						</td>
					</tr>
				`).appendTo($('#listapersonale'));
			}
		})
	}
	
	let idazienda = -1;
	
	
	
	$('#container-body').on('click', '.dettaglioazienda', function() {
		idazienda = +$(this).attr('data-id');
		nascondiElementi();
		$('#dettaglioazienda').css('display', 'block');
		$('#mostra-aggiungi-azienda').css('display', 'inline');
		$('#personaleazienda').css('display', 'block');
		$('#titolo').css('display', 'inline');
		$('#personaleazienda').html('');
		
		getAzienda(idazienda);
	})

	
	function getAzienda(idazienda) {
		
		$.get(`/aziende/${idazienda}`, function(res) {
			$(`<h2>${res.ragione_sociale}</h2>
			<p><strong><em>P. Iva:</strong></em> ${res.partita_iva} </p>
			<p><strong><em>Indirizzo:</strong></em> ${res.indirizzo} </p>
			<p><strong><em>email:</strong></em> ${res.email} </p>
			<p><strong><em>Telefono:</strong></em> ${res.n_telefono}</p>
			<img src="assets/images/${res.ragione_sociale}.jpg" alt="${res.ragione_sociale}" width="200px" height="200px" hspace="15">
			`).appendTo('#dettaglioazienda')
		})
		
		$.get(`/personale/dipendenti/${idazienda}`, function(resp) {
			
			for(let i = 0; i < resp.length; i++) {
					$(`		
						<li>
							${resp[i].nome}  ${resp[i].cognome} <p><strong>Ruolo:</strong> ${resp[i].ruolo} </p>
							<br>
						</li>
						`).appendTo('#personaleazienda')
						
			}
			
		})
		
		}


	
	$('#container-body').on('click', '.dettagliopersonale', function() {
		const id_personale = +$(this).attr('data-id');
		nascondiElementi();
		$('#mostra-aggiungi-personale').css('display', 'inline');
		
		$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris == "" || ris.includes('USER')) {
					alert('Devi essere admin per visualizzare il dettaglio')
					$('#container-body').load("listapersonale.html", function() {
					setTimeout(function(){ getPersonale() }, 200);
					$('#mostra-aggiungi-personale').css('display', 'none');
		});
				} else {
					getDipendente(id_personale);
				}
				
			})
		})

		
		
	})
	
		function getDipendente(id_personale) {
		
		$.get(`/personale/${id_personale}`, function(res) {
			$(`<h2>${res.nome} ${res.cognome}</h2>
			<p><strong><em>Data di Nascita:</strong></em> ${res.dob} </p>
			<p><strong><em>Stipendio:</strong></em> ${res.stipendio} &euro; </p>
			<p><strong><em>Data Assunzione:</strong></em> ${res.data_assunzione} </p>
			<p><strong><em>Ruolo:</strong></em> ${res.ruolo}</p>
			<img src="assets/images/dipendenti/${id_personale}.jpg" alt="Foto Dipendente" width="200px" height="200px" hspace="15" >
			<p><strong><em>Azienda:</strong></em> <a href='#container-body' class='aziendadapersonale' data-id='${res.aziende.azienda_id}'>${res.aziende.ragione_sociale}</p>
			`).appendTo('#dettagliopersonale')
		})
	}
	
	$('#dettagliopersonale').on('click', '.aziendadapersonale', function() {
		idazienda = +$(this).attr('data-id');
		nascondiElementi();
		$('#dettaglioazienda').css('display', 'block');
		$('#personaleazienda').css('display', 'block');
		getAzienda(idazienda);
	})
	
	
	$('#container-body').on('click', '.eliminaazienda', function() {
		const id = +$(this).attr('data-id');
		
		deleteAzienda(id, $(`#row-${id}`));
	
})

	function deleteAzienda(id, HTMLrow) {
		$.ajax({
			url: `aziende/delete/${id}`,
			type: 'DELETE',
			success: function() {
				HTMLrow.remove();
				$('#listaaziende').html('');
				getAziende();
			},
			error: function() {
				alert('Non hai i permessi')	
			}
			
		})
	}
	
	$('#container-body').on('click', '.eliminapersonale', function() {
		const id = +$(this).attr('data-id');
		
		deletePersonale(id, $(`#row-${id}`));
	
})

	function deletePersonale(id, HTMLrow) {
		$.ajax({
			url: `personale/delete/${id}`,
			type: 'DELETE',
			success: function() {
				HTMLrow.remove();
				$('#listapersonale').html('');
				getPersonale();
			},
			error: function() {
				alert('Non hai i permessi')	
			}
		})
	}
	
	$('#mostra-aggiungi-azienda').click(function() {
		$('#aggiungiazienda').css('display', 'block');
	
			$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris == ""|| ris.includes('USER') || ris.includes('ADMIN')) {
					alert('Non hai i permessi')
					$('#aggiungiazienda').load("vuoto.html");
				} else {$('#aggiungiazienda').load("aggiungiazienda.html");}
				
			})
		})
		
	})
	
	//$('#add-azienda').click(function() {
		$('#aggiungiazienda').on('click', '#add-azienda', function() {
		const azienda = {
			ragione_sociale: $('#ragione_sociale').val(),
			partita_iva: $('#partita_iva').val(),
			indirizzo: $('#indirizzo').val(),
			email: $('#email').val(),
			n_telefono: $('#n_telefono').val()
		}
	
		if (editMode) {
			azienda.azienda_id = editId;
			editAzienda(azienda);
			
		} else {
			addAzienda(azienda);
			
		}
		
	})
	
		function addAzienda(azienda) {
		$.ajax({
			url: '/aziende',
			type: 'POST',
			data: JSON.stringify(azienda),
			contentType: "application/json",
			success: function() {
				$('#ragione_sociale').val('');
				$('#partita_iva').val('');
				$('#indirizzo').val('');
				$('#email').val('');
				$('#n_telefono').val('');
				$('#listaaziende').html('');
				getAziende();		
			},
			error: function() {
				alert('Non hai i permessi')
			}
		})
		
	}
	
	
	$('#mostra-aggiungi-personale').click(function() {
		$('#aggiungidipendente').css('display', 'block');
		$('#aggiungidipendente').load("aggiungidipendente.html", function() {
			setTimeout(function(){ getAziendeForAdd() }, 200);
			
			$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris == "" || ris.includes('USER')) {
				
					alert('Devi loggare come ADMIN')
					$('#aggiungidipendente').load("vuoto.html");
				} else {$('#aggiungidipendente').load("aggiungidipendente.html");}
				
			})
		})
		});
		
	})
	
		function getAziendeForAdd() {
		$.get('aziende', function(res) {
			$('#azienda').html('');
			for (let i = 0; i < res.length; i++) {
					$(`<option value='${res[i].azienda_id}'>${res[i].ragione_sociale}</option>`).appendTo('#azienda');
			}
			if (editPersonaleMode) {
				$('#azienda').val(idazienda);
				idazienda = -1;
			}
		})
	}
	
	
	// $('#add-dipendente').click(function() {
	$('#aggiungidipendente').on('click', '#add-dipendente', function() {
		const dip = {
			nome: $('#nome').val(),
			cognome: $('#cognome').val(),
			dob: $('#dob').val(),
			stipendio: $('#stipendio').val(),
			data_assunzione: $('#data_assunzione').val(),
			ruolo: $('#ruolo').val(),
			aziende: { 
				azienda_id:	$('#azienda').val(),
				ragione_sociale: "",
				partita_iva:"",
				indirizzo:"",
				email:"",
				n_telefono:""
			}
			
		}
		
		if (editPersonaleMode) {
			dip.personale_id = editPersonaleId;
			editDipendente(dip);	
			
		} else {
			addDipendente(dip);
			
		}
	})
	
		function addDipendente(dip) {
		$.ajax({
			type: 'POST',
			url: '/personale',
			data: JSON.stringify(dip),
			contentType: "application/json",
			success: function() {
			$('#nome').val('');
			$('#cognome').val('');
			$('#dob').val('');
			$('#stipendio').val('');
			$('#data_assunzione').val('');
			$('#ruolo').val('');
				getPersonale();		
			}
		})
	}
	

	$('#container-body').on('click', '.modificaazienda', function() {
		const id = +$(this).attr('data-id');
		$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris == "" || ris.includes('USER') || ris.includes('ADMIN')) {
				
					alert('Non hai i permessi')
				$('#aggiungiazienda').load("vuoto.html");
				} //else {$('#aggiungiazienda').load("aggiungiazienda.html");}
				
			})
		})
		
		
		editMode = true;
		editId = id;
		
		$.ajax({
			url: `aziende/modifica/${id}`,
			type: 'GET',
			success: function(res) {
			$('#aggiungiazienda').load("aggiungiazienda.html", function() {
			
			$('#ragione_sociale').val(res.ragione_sociale);
			$('#partita_iva').val(res.partita_iva);
			$('#indirizzo').val(res.indirizzo);
			$('#email').val(res.email);
			$('#n_telefono').val(res.n_telefono);
			$('#add-azienda').text('Modifica');
			})
			
			}
			
		})
		
		
		
			$('#aggiungiazienda').css('display', 'block');
			
	
		
		
			$('#add-azienda').text('Modifica');
		
	})
	
	
	
		function editAzienda(azienda) {
		$.ajax({
			url: 'aziende',
			type: 'PUT',
			data: JSON.stringify(azienda),
			contentType: "application/json",
			success: function() {
				editMode = false;
				editId = -1;
				
				$('#add-azienda').text('Aggiungi');
				$('#listaaziende').html('');
				$('#aggiungiazienda').css('display', 'none');
			
				getAziende();
				
				
			}
		})
	}
	
	
	$('#container-body').on('click', '.modificapersonale', function() {
		const id = +$(this).attr('data-id');
		$.get('cookie', function(res) {
			
			let cookies = res.split(',');
			let sessionCookie = null;
		
			for (let i = 0; i < cookies.length; i++) {
				let cookie = cookies[i];
				if (cookie.includes('JSESSIONID')) {
					sessionCookie = cookie.substring(11);
				}
			}	 
		
			$.get(`cookie/${sessionCookie}`, function(ris) {
				console.log(ris);
				if(ris == "" || ris.includes('USER')) {
			
					alert('Devi loggare come ADMIN')
					$('#aggiungidipendente').load("vuoto.html");
				} 
				
			})
		})
		
	
		
		editPersonaleMode = true;
		editPersonaleId = id;
		
		$.ajax({
			url: `personale/modifica/${id}`,
			type: 'GET',
			success: function(res) {
			$('#aggiungidipendente').load("aggiungidipendente.html", function() {
			$('#nome').val(res.nome);
			$('#cognome').val(res.cognome);
			$('#dob').val(res.dob);
			$('#stipendio').val(res.stipendio);
			$('#data_assunzione').val(res.data_assunzione);
			$('#ruolo').val(res.ruolo);
			$('#cognome').val(res.cognome);
			
			getAziendeForAdd();
			$('#add-dipendente').text('Modifica');
			})
			// questo idazienda è l'id azienda del personale!
			idazienda = res.aziende.azienda_id;
			}
			
		})
		
	
		
		$('#aggiungidipendente').css('display', 'block');
	})
	
	function editDipendente(dip) {
		$.ajax({
			url: '/personale',
			type: 'PUT',
			data: JSON.stringify(dip),
			contentType: "application/json",
			success: function() {
				editPersonaleMode = false;
				editPersonaleId = -1;
				
				$('#add-dipendente').text('Aggiungi');
				$('#listapersonale').html('');
				$('#aggiungidipendente').css('display', 'none');
				getPersonale();
			
			}
		})
	}

})
 