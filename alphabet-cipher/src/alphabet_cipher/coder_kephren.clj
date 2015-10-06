(ns alphabet-cipher.coder_kephren)

(def alphabet (char-array "abcdefghijklmnopqrstuvwxyz"))

(def offset (int \a))

(defn nth-alpha [index]
  (nth alphabet (mod index 26)))

(defn a->idx [alpha]
  (- (int alpha) offset))

(defn seq->str [seq] (apply str seq))

(defn sieve [fn left right]
  (seq->str (map #(nth-alpha (fn (a->idx %1)
                                 (a->idx %2)))
                 left right)))

;the-michael-willis-de-cycle-plus
(defn de-cycle [raw]
  (loop [size 1]
    (let [keyword (seq->str (take size raw))
          tail (seq->str (take size (drop size raw)))]
      (if (or (= keyword tail)
              (= raw (str keyword tail) ))
        keyword
        (recur (inc size))))))

(defn encode [keyword message]
  "encodeme"
  (sieve + (cycle keyword) message))

(defn decode [keyword message]
  "decodeme"
  (sieve - message (cycle keyword)))

(defn decypher [cypher message]
  "decypherme"
  (de-cycle (sieve - cypher message)))
