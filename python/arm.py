__author__ = 'Joshua Arabia'
__contact__ = 'josh.arabia@pitt.edu'
__date__ = '23 March 2020'

import argparse
import csv
import itertools as it


def fetch_data(src_file: str):
    """Retrieves input data from the file located at 'src_file', then returns a dict containing
        a copy of all records and a list of all items parsed."""
    with open(src_file) as src:
        reader = csv.reader(src)
        records = []
        language = set([])
        for row in reader:
            rec = sorted([s.strip() if type(s) == str else s for s in row[1:]])
            records.append(rec)
            language = language.union(rec)

    return {'records': records, 'language': sorted(list(language))}


def write_data(dest_file: str, output):
    with open(dest_file, 'w') as dest:
        writer = csv.writer(dest)
        for row in output:
            writer.writerow(row.split(','))


def get_supp(item, context):
    """Calculates the support percentage of a given element within the set of records defined
        in context. The return in bounded on [0.0, 1.0]"""
    count = 0
    for elem in context:
        if all(i in elem for i in item):
            count += 1
    return count/len(context)


def to_rules(items):
    """Partitions the given item set S into (A,B), where each pair represents the rule A => B
        and A U B = S. A and B are disparate sets."""
    i_set = set(items)
    ret = []
    for i in range(1, len(items)):
        for subset in it.combinations(items, i):
            ret.append((set(subset), i_set-set(subset)))
    return ret


# Create argument parser to get command line input
parser = argparse.ArgumentParser(description='Apriori Rule Mining:')
parser.add_argument('src_file', type=str, default=None, help='Input Filename')
parser.add_argument('dest_file', type=str, default=None, help='Output Filename')
parser.add_argument('min_supp', type=float, default=0, help='Minimum Support Percentage')
parser.add_argument('min_con', type=float, default=0, help='Minimum Confidence')

args = parser.parse_args()
data = fetch_data(args.src_file)

# vfi holds the verified frequent item sets, stored as {set: supp}
# new_lang holds the updated set of possible items, and eliminates those which are not part
#   of any element in vfi
vfi, new_lang = {}, []
for candidate in data['language']:
    supp = get_supp(set(candidate), data['records'])
    if supp >= args.min_supp:
        vfi[tuple(candidate)] = supp
        new_lang = list(set(new_lang) | set(candidate))

new_lang.sort()

# card is the size of the item sets currently being tested
card = 2

# if the size of the working language is less than the size of the sets
#   to be created, then nothing will be formed, so coalescing is done
while len(new_lang) >= card:
    verified = []
    cfi = sorted(list(it.combinations(new_lang, card)))
    for candidate in cfi:
        supp = get_supp(set(candidate), data['records'])
        if supp >= args.min_supp:
            vfi[tuple(sorted(list(candidate)))] = supp
            new_lang = list(set(new_lang) | set(candidate))

    card += 1

output = []

tr_table = {ord('['): '', ord(']'): '', ord('\''): '', ord('\"'): '', ord(' '): ''}
# Expected output is sorted by length of set, then lexicographical order
for i_set in sorted(list(vfi.keys()), key=lambda x: (len(x), str(x))):
    i_string = str(sorted(i_set)).translate(tr_table)
    supp = vfi[i_set]
    output.append('S,%.4f,%s' % (supp, i_string))

cr, vr = [], {}
for i_set in vfi.keys():
    if len(i_set) > 1:
        for candidate in to_rules(i_set):
            cr.append(candidate)

for candidate in cr:
    items = tuple(candidate[0] | candidate[1])
    items = tuple(sorted(items))
    supp = vfi[items]
    if supp >= args.min_supp:
        conf = supp / vfi[tuple(sorted(list(candidate[0])))]
        if conf >= args.min_con:
            rule = (tuple(candidate[0]), tuple(candidate[1]))
            vr[rule] = (supp, conf)

tr_table = {ord('['): '', ord(']'): '', ord('\''): '', ord('\"'): '', ord(' '): ''}
for rule in sorted(list(vr.keys()), key=lambda x: (len(x[0]) + len(x[1]), x[0], x[1])):
    # print(rule)
    a_string = str(sorted(list(rule[0]))).translate(tr_table)
    b_string = str(sorted(list(rule[1]))).translate(tr_table)
    supp = vr[rule][0]
    conf = vr[rule][1]
    output.append('R,%.4f,%.4f,%s,\'=>\',%s' % (supp, conf, a_string, b_string))

write_data(args.dest_file, output)
