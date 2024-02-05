<template>
  
  <div class="overflow-auto">
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="my-table"
    ></b-pagination>

    <p class="mt-3">Current Page: {{ currentPage }}</p>
    <b-input placeholder="apunta algo" v-model="filtro"></b-input>
    <b-table class="mt-5"
      id="my-table"
      :items="personas"
      :per-page="perPage"
      :current-page="currentPage"
      :fields="fields"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      label-sort-asc=""
      label-sort-desc=""
      small
      :filter="filtro"
      @filtered="onFiltered"
    ></b-table>
  </div>
</template>

<script>
import personas from "../services/personas";

export default {
  data() {
    return {
      filtro: null,
      sortBy: "name",
      sortDesc: false,
      perPage: 5,
      currentPage: 1,
      personas: [],
      rows: 0,
      fields: [
        { key: "name", label: "Nombre", sortable: true },
        { key: "firstname", label: "Apellido Paterno", sortable: true },
        { key: "lastname", label: "Apellido Materno", sortable: true },
        { key: "address", label: "Direccion", sortable: true },
        { key: "birthday", label: "Fech. Nac.", sortable: true },
        { key: "email", label: "Email", sortable: true },
        { key: "email", label: "Email", sortable: true },
      ],
    };
  },
  mounted() {
    this.obtenerPersonas();
  },
  methods: {
    async obtenerPersonas() {
      try {
        const data = await personas.obtenerPersonasPaginadas(
          parseInt(this.currentPage),
          parseInt(this.perPage),
          this.filtro 
        );
        this.personas = data.content;
        this.rows = data.totalElements;
      } catch (error) {
        console.error(error);
      }
    },
    onFiltered(filteredItems) {
      this.currentPage = 1;
      this.rows = filteredItems.length;
    },
  },
};
</script>
