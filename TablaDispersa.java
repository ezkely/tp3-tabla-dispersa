class TablaDispersa {
    private Tarea[] tabla;
    private int numElementos;
    private double factorCarga;
    private final int CAPACIDAD = 101;
    private final double A = 0.6180339887;

    public TablaDispersa() {
        this.tabla = new Tarea[CAPACIDAD];
        this.numElementos = 0;
        this.factorCarga = 0.0;
    }

    public int calcularPosicion(String codigo) {
        double valor = obtenerValorNumerico(codigo);
        double producto = valor * A;
        double decimal = producto - Math.floor(producto);
        return (int) (decimal * CAPACIDAD);
    }

    private int obtenerValorNumerico(String codigo) {
        String clave = codigo.length() > 10 ? codigo.substring(0, 10) : codigo;
        long suma = 0;
        for (char c : clave.toCharArray()) {
            suma += (int) c;
        }
        return (int) (suma % Integer.MAX_VALUE);
    }

    public int resolverColision(int posicionInicial, int i) {
        return (posicionInicial + i * i) % CAPACIDAD;
    }

    public boolean insertar(Tarea t) {
        if (numElementos >= CAPACIDAD) return false;

        int pos = calcularPosicion(t.getCodigo());
        int i = 0;

        while (tabla[pos] != null && tabla[pos].isAlta()) {
            if (tabla[pos].getCodigo().equals(t.getCodigo())) return false;
            pos = resolverColision(pos, ++i);
        }

        tabla[pos] = t;
        numElementos++;
        calcularFactorCarga();
        return true;
    }

    public Tarea buscarPorNombre(String nombre) {
        for (Tarea t : tabla) {
            if (t != null && t.isAlta() && t.getNombre().equalsIgnoreCase(nombre)) {
                return t;
            }
        }
        return null;
    }

    public boolean eliminarPorNombre(String nombre) {
        for (Tarea t : tabla) {
            if (t != null && t.isAlta() && t.getNombre().equalsIgnoreCase(nombre)) {
                t.setAlta(false);
                return true;
            }
        }
        return false;
    }

    public double calcularFactorCarga() {
        this.factorCarga = (double) numElementos / CAPACIDAD;
        return factorCarga;
    }

    public void mostrarTabla() {
        System.out.println("\n=========== TABLA DE TAREAS ===========");
        for (int i = 0; i < tabla.length; i++) {
            Tarea t = tabla[i];
            if (t != null && t.isAlta()) {
                System.out.printf("\nPosición [%03d] ➤ %s\n", i, t.getNombre());
                System.out.println(t);
            }
        }
        System.out.printf("=== Factor de carga: %.2f ===\n", calcularFactorCarga());
    }
}
