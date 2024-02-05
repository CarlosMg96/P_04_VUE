<template>
  <div class="container mt-4">
    <h2>Formulario de Registro</h2>
    <b-form @submit.prevent="submitForm">
      <!-- Nombre -->
      <b-form-group id="nameGroup" label="Nombre" label-for="name" :state="nameState">
        <b-input id="name" v-model="name" placeholder="Nombre" @input="validateName"></b-input>
        <b-form-invalid-feedback :state="nameState">
          Ingresa un nombre válido.
        </b-form-invalid-feedback>
      </b-form-group>

      <!-- Apellidos -->
      <b-form-group id="lastNameGroup" label="Apellido(s)" label-for="lastName" :state="lastNameState">
        <b-input id="lastName" v-model="lastName" placeholder="Apellido(S)" @input="validateLastName"></b-input>
        <b-form-invalid-feedback :state="lastNameState">
          Ingresa un apellido válido.
        </b-form-invalid-feedback>
      </b-form-group>

      <!-- Dirección -->
      <b-form-group label="Dirección">
        <b-input-group>
          <b-input-group-prepend is-text>CP</b-input-group-prepend>
          <b-form-input v-model="cp" placeholder="Código Postal"></b-form-input>
        </b-input-group>
        <b-input-group>
          <b-input-group-prepend is-text>Calle</b-input-group-prepend>
          <b-form-input v-model="calle" placeholder="Calle"></b-form-input>
        </b-input-group>
        <b-input-group>
          <b-input-group-prepend is-text>Número</b-input-group-prepend>
          <b-form-input v-model="numero" placeholder="Número"></b-form-input>
        </b-input-group>
        <b-input-group>
          <b-input-group-prepend is-text>Ciudad</b-input-group-prepend>
          <b-form-input v-model="ciudad" placeholder="Ciudad"></b-form-input>
        </b-input-group>
      </b-form-group>

      <!-- Fecha de Nacimiento -->
      <b-form-group label="Fecha de Nacimiento">
        <b-form-datepicker v-model="fechaNacimiento" :max="maxDate"
          placeholder="Selecciona una fecha"></b-form-datepicker>
      </b-form-group>

      <!-- Correo Electrónico -->
      <b-form-group id="emailGroup" label="Correo Electrónico" label-for="email" :state="emailState">
        <b-input id="email" v-model="email" placeholder="Correo Electrónico" @input="validateEmail"></b-input>
        <b-form-invalid-feedback :state="emailState">
          Ingresa un correo electrónico válido.
        </b-form-invalid-feedback>
      </b-form-group>

      <!-- Número Telefónico -->
      <b-form-group id="phoneGroup" label="Número Telefónico" label-for="phone" :state="phoneState">
        <b-input id="phone" v-model="phone" placeholder="Número Telefónico" @input="validatePhone"></b-input>
        <b-form-invalid-feedback :state="phoneState">
          Ingresa un número telefónico válido.
        </b-form-invalid-feedback>
      </b-form-group>

      <!-- Fotografía -->
      <b-form-group>
        <b-form-file id="photo" v-model="photo" :state="photoState" accept=".png" :max-size="3000000"
          placeholder="Selecciona una foto (PNG, max 3MB)"></b-form-file>
        <b-form-invalid-feedback :state="photoState">
          Selecciona una foto en formato PNG y que sea menor a 3MB.
        </b-form-invalid-feedback>
      </b-form-group>

      <!-- Botón de Envío -->
      <b-button type="submit" variant="primary">Enviar</b-button>
    </b-form>
    <b-alert v-if="viewAlertSuccess" variant="success" dismissible >
      ¡Éxito! Tú información es correcta.
    </b-alert>
    <b-alert v-if="viewAlertError" variant="warning" dismissible >
      ¡Error! Tú información no es valida, vuelve a confirmar tu info.
    </b-alert>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue';

const name = ref('');
const nameState = ref(null);
const lastName = ref('');
const lastNameState = ref(null);

const cp = ref('');
const calle = ref('');
const numero = ref('');
const ciudad = ref('');

const fechaNacimiento = ref(null);
const maxDate = new Date(new Date().getFullYear() - 18, new Date().getMonth(), new Date().getDate());

const email = ref('');
const emailState = ref(null);

const phone = ref('');
const phoneState = ref(null);

const photo = ref(null);
const photoState = ref(null);

const viewAlertSuccess = ref(false);
const viewAlertError = ref(false);

// Funciones de validación
const validateName = () => {
  //Función que válida el nombre
  const nameRegex = /^[A-Za-z]+$/;
  const isValidName = name.value && nameRegex.test(name.value);
  nameState.value = isValidName ? true : false;
};

const validateLastName = () => {
  const nameRegex = /^[A-Za-z]+$/;
  const isValidLastName = lastName.value && nameRegex.test(lastName.value);
  lastNameState.value = isValidLastName ? true : false;
};

const validateEmail = () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const isValidEmail = emailRegex.test(email.value);
  emailState.value = isValidEmail ? true : false;
};

const validatePhone = () => {
  const phoneRegex = /^[0-9]{10}$/;
  const isValidPhone = phoneRegex.test(phone.value);
  phoneState.value = isValidPhone ? true : false;
};

// Verificar cambios en la fecha de nacimiento para volver a validar el nombre completo
watch(fechaNacimiento, () => {
  validateName();
  validateLastName();
});

// Función para manejar el envío del formulario
const submitForm = () => {
  // Volver a validar si es correcto
  validateName();
  validateLastName();
  validateEmail();
  validatePhone();

  if (nameState.value && lastNameState.value && emailState.value && phoneState.value && photoState.value) {
    console.log('Formulario enviado correctamente:', {
      name: name.value,
      lastName: lastName.value,
      address: { cp, calle, numero, ciudad },
      fechaNacimiento: fechaNacimiento.value,
      email: email.value,
      phone: phone.value,
      photo: photo.value,
    });
    viewAlertSuccess.value = true;
  }else{
    console.log("Los datos estan incompletos o son incorrectos");
    viewAlertError.value = true;
    console.log('El formulario tiene un error', {
      name: name.value,
      lastName: lastName.value,
      address: { cp, calle, numero, ciudad },
      fechaNacimiento: fechaNacimiento.value,
      email: email.value,
      phone: phone.value,
      photo: photo.value,
    });
  }
};
</script>

<style scoped>

</style>
